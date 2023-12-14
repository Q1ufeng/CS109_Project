package model;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    //这一盘的分数
    private int score;

    public Chessboard(int randomSeed) {
        this.grid = new Cell[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
        this.score=0;

        initGrid();
        initPieces(randomSeed);
    }

    private void initGrid() {
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces(int randomSeed) {
        Random random = new Random(randomSeed);
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
            }
        }
        //判断当下生成的棋盘是否满足初始化棋盘条件，不满足则重新生成
        while(!isValid(this)){
            for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                    grid[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
                }
            }
        }
    }

    public boolean isValid(Chessboard chessboard){
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum()-2; i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum()-2; j++) {
                if((grid[i][j].getPiece().getName().equals(grid[i+1][j].getPiece().getName())&&grid[i][j].getPiece().getName().equals(grid[i+2][j].getPiece().getName()))||(grid[i][j].getPiece().getName().equals(grid[i][j+1].getPiece().getName())&&grid[i][j].getPiece().getName().equals(grid[i][j+2].getPiece().getName()))){
                    return false;
                }
            }
        }
        for(int i=CHESSBOARD_ROW_SIZE.getNum()-2;i<CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<CHESSBOARD_COL_SIZE.getNum()-2; j++){
                if(((grid[i][j].getPiece().getName().equals(grid[i][j+1].getPiece().getName())&&grid[i][j].getPiece().getName().equals(grid[i][j+2].getPiece().getName())))){
                    return false;
                }
            }
        }
        for(int i=0;i<CHESSBOARD_ROW_SIZE.getNum()-2;i++){
            for(int j=CHESSBOARD_COL_SIZE.getNum()-2;j<CHESSBOARD_COL_SIZE.getNum(); j++){
                if((grid[i][j].getPiece().getName().equals(grid[i+1][j].getPiece().getName())&&grid[i][j].getPiece().getName().equals(grid[i+2][j].getPiece().getName()))){
                    return false;
                }
            }
        }
        return true;
    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }


    public void swapChessPiece(ChessboardPoint point1, ChessboardPoint point2) {
        var p1 = getChessPieceAt(point1);
        var p2 = getChessPieceAt(point2);
        setChessPiece(point1, p2);
        setChessPiece(point2, p1);
    }


    public Cell[][] getGrid() {
        return grid;
    }

    //检查现在的棋盘能不能转化为分数并消除（如果是0当然就意味着不能三消除）
    public int checkScore()
    {
        int score=0;
        int [][]signal=new int[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_ROW_SIZE.getNum()];
        //用来在检查过程中标记一个格点是否已经被认定为应当消除，0是不应当消除，1是应当消除
        for(int i=0;i<CHESSBOARD_ROW_SIZE.getNum();i++)
            for(int j=0;j<CHESSBOARD_ROW_SIZE.getNum();j++)
                signal[i][j]=0;
        for(int i=0;i<=CHESSBOARD_ROW_SIZE.getNum()-1;i++)
            for(int j=0;j<CHESSBOARD_ROW_SIZE.getNum()-2;j++)
            {
                if(getGrid()[i][j].equals(getGrid()[i][j+1])&&getGrid()[i][j+1].equals(getGrid()[i][j+2]))
                {
                    signal[i][j]=signal[i][j+1]=signal[i][j+2]=1;
                }
            }
        for(int i=0;i<=CHESSBOARD_ROW_SIZE.getNum()-1;i++)
            for(int j=0;j<CHESSBOARD_ROW_SIZE.getNum()-2;j++)
            {
                if(getGrid()[j][i].equals(getGrid()[j+1][i])&&getGrid()[j+1][i].equals(getGrid()[j+2][i]))
                {
                    signal[j][i]=signal[j+1][i]=signal[j+2][i]=1;
                }
            }
        for(int i=0;i<=CHESSBOARD_ROW_SIZE.getNum()-1;i++)
            for(int j=0;j<=CHESSBOARD_ROW_SIZE.getNum()-1;j++)
                if(signal[i][j]==1)
                {
                    score+=10;
                    removeChessPiece(new ChessboardPoint(i,j));
                }
        setScore(getScore()+score);
        return score;
    }

    //实现消除后下落功能的函数
    public boolean fallDown()
    {
        //ret表征着是否真的有棋子在下落
        boolean ret=false;
        for(int i=CHESSBOARD_ROW_SIZE.getNum()-1;i>=1;i--)
            for(int j=0;j<=CHESSBOARD_ROW_SIZE.getNum()-1;j++)
                if(getGrid()[i][j].getPiece()==null)
                {
                    for(int k=i-1;k>=0;k--)
                        if(getGrid()[k][j].getPiece()!=null)
                        {
                            getGrid()[i][j].setPiece(getGrid()[k][j].getPiece());
                            getGrid()[k][j].removePiece();
                            ret=true;
                            break;
                        }
                }
        //返回一个true来代表函数成功运行惹
        return ret;
    }
    //实现消除并下落后在上方的空方格内随机刷新棋子的函数
    public boolean fresh()
    {
        //ret表征着是否真的有棋子刷新
        boolean ret=false;
        //用一个list来存储需要刷新棋子的格子
        List<Point> needFresh=new ArrayList<>();
        for(int i=0;i<=CHESSBOARD_ROW_SIZE.getNum()-1;i++)
            for(int j=0;j<=CHESSBOARD_ROW_SIZE.getNum()-1;j++)
                if(getGrid()[i][j].getPiece()==null){
                    needFresh.add(new Point(i,j));
                    ret=true;
                }
        //随机刷新棋子
        for(Point a:needFresh)
            getGrid()[a.x][a.y].setPiece(new ChessPiece( Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
        return ret;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    //判断当前棋盘是否为死局的函数，返回true意味着是死局
    public boolean isDead()
    {
        //将本棋盘的信息拷贝到函数中，因为我认为不搞传参会比较优雅
        Chessboard copy=this;
        //这部分判断左右交换之后有没有可能得分
        for(int i=0;i<=CHESSBOARD_ROW_SIZE.getNum()-1;i++)
            for(int j=0;j<=CHESSBOARD_ROW_SIZE.getNum()-2;j++)
            {
                //在copy中交换i，j与右边一格
                Cell temp=copy.getGrid()[i][j];
                copy.getGrid()[i][j]=copy.getGrid()[i][j+1];
                copy.getGrid()[i][j+1]=temp;
                //判断交换之后有没有分,如果没有就换回去，有就直接返回false
                if(copy.checkScore()!=0) return false;
                else
                {
                    Cell temp2=copy.getGrid()[i][j];
                    copy.getGrid()[i][j]=copy.getGrid()[i][j+1];
                    copy.getGrid()[i][j+1]=temp2;
                }
            }
        //这部分判断上下交换之后有没有可能得分
        for(int i=0;i<=CHESSBOARD_ROW_SIZE.getNum()-1;i++)
            for(int j=0;j<=CHESSBOARD_ROW_SIZE.getNum()-2;j++)
            {
                Cell temp=copy.getGrid()[j][i];
                copy.getGrid()[j][i]=copy.getGrid()[j+1][i];
                copy.getGrid()[j+1][i]=temp;
                if(copy.checkScore()!=0) return false;
                else
                {
                    Cell temp2=copy.getGrid()[j][i];
                    copy.getGrid()[j][i]=copy.getGrid()[j+1][i];
                    copy.getGrid()[j+1][i]=temp2;
                }
            }
        return true;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }
    /*
         关于存档信息：
         存档存在整个游戏目录下的一个.txt文件中
         每4行为一个存档
        分别为
        IDSCORE分数（数字）
        IDSTEP剩余步数（数字）
        IDLEV关卡（数字）
        IDBOARD棋盘每格的棋子（用1-4表示，长字符串）
         */

    //当没有此号（ID）存档时写入
    public void addSave(int id) throws IOException {
        String filepathname="Java_Project\\Saves";
        Path filepath= Paths.get(filepathname);
        List<String> info= Files.readAllLines(filepath);
        info.add(String.format("%dSCORE%d",id,this.getScore()));
        //TODO:加入步数和关卡之后把下面两行和步数关卡有关的存档补上
        info.add(String.format("%dSTEP10",id));
        info.add(String.format("%dLEV10",id));
        String boardInfo=String.format("%dBOARD",id);
        for(int i=0;i<=CHESSBOARD_ROW_SIZE.getNum()-1;i++)
            for(int j=0;j<=CHESSBOARD_ROW_SIZE.getNum()-1;j++)
                boardInfo+=String.format("%d",getGrid()[i][j].getPiece().getId());
        info.add(boardInfo);
        Files.write(filepath,info);
    }
    //当存在此号（ID）存档时读取,返回一个Chessboard
    public static Chessboard loadSave(int id) throws IOException
    {
        Chessboard ret = new Chessboard(0);
        String filepathname="Java_Project\\Saves";
        Path filepath= Paths.get(filepathname);
        List<String> allInfo= Files.readAllLines(filepath);
        for(String a:allInfo)
        {
            if(a.contains(String.format("%dSCORE",id)))
            {
                String score=a.substring(6);
                ret.setScore(Integer.parseInt(score));
            }
            else if(a.contains(String.format("%dSTEP",id)))
            {
                String step=a.substring(5);
                //TODO:设置好步数之后把这里加一句设步数的
            }
            else if(a.contains(String.format("%dLEV",id)))
            {
                String lev=a.substring(4);
                //TODO:设置好分数之后把这里加一句设分数的
            }
            else if(a.contains(String.format("%dBOARD",id)))
            {
                String board=a.substring(6);
                Cell[][]theGrid=new Cell[CHESSBOARD_ROW_SIZE.getNum()-1][CHESSBOARD_ROW_SIZE.getNum()-1];
                for(int i=0;i<=CHESSBOARD_ROW_SIZE.getNum()-1;i++)
                    for(int j=0;j<=CHESSBOARD_ROW_SIZE.getNum()-1;j++)
                        theGrid[i][j].setPiece(ChessPiece.pieceById(Integer.parseInt(String.valueOf(board.charAt(i*8+j)))));
                ret.setGrid(theGrid);
            }
        }
        return ret;
    }
    //判断这一号存档是否已经存在
    public boolean haveID(int id) throws IOException
    {
        String filepathname="Java_Project\\Saves";
        Path filepath= Paths.get(filepathname);
        List<String> allInfo= Files.readAllLines(filepath);
        for(String a:allInfo)
            if(a.contains(String.format("%dSCORE",id))) return true;
        return false;
    }
    //删除这一号存档
    // TODO:在制作存档模块功能的时候郭子来灵活组合这几个功能块捏
    public void deleteID(int id) throws IOException {
        String filepathname="Java_Project\\Saves";
        Path filepath= Paths.get(filepathname);
        List<String> allInfo= Files.readAllLines(filepath);
        List<String> after=new ArrayList<>();
        for(String a:allInfo)
            if(!a.contains(String.format("%dSCORE"))&&(!a.contains(String.format("%dSTEP",id)))&&(!a.contains(String.format("%dLEV",id)))&&(!a.contains(String.format("%dBOARD",id)))) after.add(a);
        Files.write(filepath,after);
    }
}