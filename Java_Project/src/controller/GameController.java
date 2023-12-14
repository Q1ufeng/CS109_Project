package controller;

import listener.GameListener;
import model.ChessPiece;
import model.Constant;
import model.Chessboard;
import model.ChessboardPoint;
import model.Cell;
import view.CellComponent;
import view.ChessComponent;
import view.ChessboardComponent;
import view.ChessGameFrame;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 *
 */
public class GameController implements GameListener {

    private Chessboard model;
    private ChessboardComponent view;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;
    private ChessGameFrame gameFrame;

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    public void setGameFrame(ChessGameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public Chessboard getModel() {
        return model;
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
    }

    @Override
    public void onPlayerSwapChess(){
        // TODO: Init your swap function here.
        var point1 = (ChessComponent)view.getGridComponentAt(selectedPoint).getComponent(0);
        var point2 = (ChessComponent)view.getGridComponentAt(selectedPoint2).getComponent(0);
        view.setChessComponentAtGrid(selectedPoint,point2);
        view.setChessComponentAtGrid(selectedPoint2,point1);
        point1.repaint();
        point2.repaint();
        model.swapChessPiece(selectedPoint,selectedPoint2);
        if(model.isValid(model)){
            view.setChessComponentAtGrid(selectedPoint2,point2);
            view.setChessComponentAtGrid(selectedPoint,point1);
            point1.repaint();
            point2.repaint();
            model.swapChessPiece(selectedPoint,selectedPoint2);
        }
        else
        {   model.checkScore();
            gameFrame.setScore();
            gameFrame.Score.repaint();
            //使用改进后的while语句，保证先进行一次分数检查，然后下落和刷新，直到分数检查发现无法消除
            //while(model.checkScore()!=0) {
                //model.fallDown();
                //model.fresh();
            //}
            //TODO: 到这里棋盘的刷新（应该）已经完成了，但我实在是画不出来，郭子加油
            view.setVisible(false);
            view.initiateChessComponent(model,0);
            view.repaint();
            view.setVisible(true);
            selectedPoint=selectedPoint2=null;
        }
    }

    @Override
    public void onPlayerNextStep(){
        // TODO: Init your next step function here.
        if(!model.fallDown()){
            if(!model.fresh()){
                model.checkScore();
                gameFrame.setScore();
                gameFrame.Score.repaint();
            }

        }


        view.setVisible(false);
        view.initiateChessComponent(model,0);
        view.repaint();
        view.setVisible(true);
    }

    public static boolean isValid(Chessboard chessboard){
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum()-2; i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum()-2; j++) {
                if((grid[i][j].getPiece().equals(grid[i+1][j].getPiece())&&grid[i][j].getPiece().equals(grid[i+2][j].getPiece()))||(grid[i][j].getPiece().equals(grid[i][j+1].getPiece())&&grid[i][j].getPiece().equals(grid[i][j+2].getPiece()))){
                    return false;
                }
            }
        }
        for(int i=CHESSBOARD_ROW_SIZE.getNum()-2;i<CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<CHESSBOARD_COL_SIZE.getNum()-2; j++){
                if(((grid[i][j].getPiece().equals(grid[i][j+1].getPiece())&&grid[i][j].getPiece().equals(grid[i][j+2].getPiece())))){
                    return false;
                }
            }
        }
        for(int i=0;i<CHESSBOARD_ROW_SIZE.getNum()-2;i++){
            for(int j=CHESSBOARD_COL_SIZE.getNum()-2;j<CHESSBOARD_COL_SIZE.getNum(); j++){
                if((grid[i][j].getPiece().equals(grid[i+1][j].getPiece())&&grid[i][j].getPiece().equals(grid[i+2][j].getPiece()))){
                    return false;
                }
            }
        }
        return true;
    }
    //判断棋盘是否有空格
    public static boolean hasNull(Chessboard model){
        for(int i=0;i<CHESSBOARD_ROW_SIZE.getNum()-1;i++){
            for(int j=0;j<CHESSBOARD_COL_SIZE.getNum()-1;j++){
                if(model.getGrid()[i][j].getPiece()==null)
                    return true;
            }
        }
        return false;
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if(selectedPoint2 != null){
            var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
            var distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
            var point1 = (ChessComponent)view.getGridComponentAt(selectedPoint).getComponent(0);
            var point2 = (ChessComponent)view.getGridComponentAt(selectedPoint2).getComponent(0);
            if(distance2point1 == 0 && point1!= null){
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = null;
            }else if(distance2point2 == 0 && point2!= null){
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = null;
            }else if(distance2point1 == 1 && point2!= null){
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }else if(distance2point2 == 1 && point1!= null){
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }
            return;
        }

        
        if (selectedPoint == null) {
            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
            return;
        }
        
        var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow()); 
        
        if(distance2point1 == 0){
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            return;
        }

        if(distance2point1 == 1){
            selectedPoint2 = point;
            component.setSelected(true);
            component.repaint();
        }else{
            selectedPoint2 = null;
            
            var grid = (ChessComponent)view.getGridComponentAt(selectedPoint).getComponent(0);
            if(grid == null) return;            
            grid.setSelected(false);
            grid.repaint();
            
            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
        }
            
        
    }
}
