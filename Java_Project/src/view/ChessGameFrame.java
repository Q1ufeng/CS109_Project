package view;

import javax.swing.*;
import java.awt.*;
import controller.GameController;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGHT;

    private final int ONE_CHESS_SIZE;

    private ChessboardComponent chessboardComponent;
    public ChessGameFrame(int width, int height) {
        setTitle("Match-3 Games"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.ONE_CHESS_SIZE = (HEIGHT * 4 / 5) / 9;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addTitle();
        addPlayButton();
        addContinueButton();

        addChessboard(0);
        addSwapConfirmButton();
        addNextStepButton();
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public

    void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        //chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGHT / 5, HEIGHT / 10);
        add(chessboardComponent);
    }
    private void addChessboard(int i) {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        //chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        //add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("Sample label");
        statusLabel.setLocation(HEIGHT, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        //add(statusLabel);
    }
    public JLabel Score;
    private void addScore() {
        Score = new JLabel("Score:"+chessboardComponent.getGameController().getModel().getScore());
        Score.setLocation(HEIGHT, HEIGHT / 10);
        Score.setSize(200, 60);
        Score.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(Score);
    }
    public void setScore(){
        Score.setVisible(false);
        Score = new JLabel("Score:"+chessboardComponent.getGameController().getModel().getScore());
        Score.setLocation(HEIGHT, HEIGHT / 10);
        Score.setSize(200, 60);
        Score.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(Score);
    }

    private void addTitle(){
        JLabel statusLabel = new JLabel("Match-3");
        statusLabel.setLocation(WIDTH/2-85, 10);
        statusLabel.setSize(190, 100);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 40));
        add(statusLabel);
    }
    private JButton NewGameButton;
    private void addPlayButton(){
        NewGameButton = new JButton("NEW GAME");
        NewGameButton.addActionListener((e) -> {
            NewGameButton.setVisible(false);
            Continue.setVisible(false);
            //addChessboard();
            //addSwapConfirmButton(0);
            //addNextStepButton(0);
            addLevel();
            });
        NewGameButton.setLocation(WIDTH/2-150, HEIGHT / 5);
        NewGameButton.setSize(300, 100);
        NewGameButton.setFont(new Font("Rockwell", Font.BOLD, 35));
        add(NewGameButton);
    }
    private JButton Left,Right,Back;
    private JButton[] Level=new JButton[7];
    private int level=0;

    private void addLevel(){
        Left= new JButton("<<");
        Left.addActionListener((e)->{
            if(level>0){
                level--;
            }
            if(level==0){
                Left.setVisible(false);
            }
            for(int i=0;i<7;i++){
                Level[i].setVisible(false);
            }
            Level[level].setVisible(true);
            Right.setVisible(true);
            Right.repaint();
        });
        Left.setLocation(WIDTH/4-200,HEIGHT/2);
        Left.setSize(100,50);
        Left.setFont(new Font("Rockwell", Font.BOLD, 25));
        add(Left);
        Left.setVisible(false); 

        Right=new JButton(">>");
        Right.addActionListener((e)->{
            if(level<6){
                level++;
            }
            if(level==6){
                Right.setVisible(false);
            }
            for(int i=0;i<7;i++){
                Level[i].setVisible(false);
            }
            Level[level].setVisible(true);
            Left.setVisible(true);
            Left.repaint();
        });
        Right.setLocation(WIDTH*3/4+100,HEIGHT/2);
        Right.setSize(100,50);
        Right.setFont(new Font("Rockwell", Font.BOLD, 25));
        add(Right);
        Right.repaint();



        Back=new JButton("<-Back");
        Back.addActionListener((e)->{
            Continue.setVisible(true);
            NewGameButton.setVisible(true);
            Back.setVisible(false);
            Left.setVisible(false);
            Right.setVisible(false);
            for(int i=0;i<7;i++){
                Level[i].setVisible(false);
            }
        });
        Back.setLocation(50,50);
        Back.setSize(120, 60);
        Back.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(Back);
        Back.repaint();

        for(int i=0;i<7;i++){
            Level[i]=new JButton("Level "+(i+1));
            Level[i].addActionListener((e)->{
                Back.setVisible(false);
                for(int j=0;j<7;j++)
                    Level[j].setVisible(false);
                Right.setVisible(false);
                Left.setVisible(false);
                addChessboard();
                addSwapConfirmButton(0);
                addNextStepButton(0);
                addScore();
            });
            Level[i].setLocation(WIDTH/2-100,HEIGHT/2);
            Level[i].setSize(200,100);
            Level[i].setFont(new Font("Rockwell", Font.BOLD, 30));
            add(Level[i]);
            if(i==level)
                Level[i].repaint();
        }

    }

    private JButton Continue;
    private void addContinueButton(){
        Continue=new JButton("CONTINUE");
        Continue.addActionListener((e) -> {
            Continue.setVisible(false);
            NewGameButton.setVisible(false);
            addContinueBack();
            addFiles();
            for(int i=0;i<8;i++){
                files[i].repaint();
            }
            });
        Continue.setLocation(WIDTH/2-150, HEIGHT / 5+150);
        Continue.setSize(300, 100);
        Continue.setFont(new Font("Rockwell", Font.BOLD, 35));
        add(Continue);
    }

    private JButton[] files=new JButton[8];
    private void addFiles(){
        for(int i=0;i<4;i++){
            files[i]=new JButton("save"+(i+1));
            files[i].addActionListener((e)->{
                for(int j=0;j<8;j++){
                    files[j].setVisible(false);
                }
                addChessboard();//导入
                addSwapConfirmButton(0);
                addNextStepButton(0);
                ContinueBack.setVisible(false);
            });
            files[i].setLocation(WIDTH/4-200+((WIDTH/2-400)/3+200)*i,HEIGHT/3);
            files[i].setSize(200,100);
            files[i].setFont(new Font("Rockwell", Font.BOLD, 25));
            add(files[i]);
        }
        for(int i=4;i<8;i++){
            files[i]=new JButton("save"+(i+1));
            files[i].addActionListener((e)->{
                for(int j=0;j<8;j++){
                    files[j].setVisible(false);
                }
                addChessboard();//导入
                addSwapConfirmButton(0);
                addNextStepButton(0);
                ContinueBack.setVisible(false);
            });
            files[i].setLocation(WIDTH/4-200+((WIDTH/2-400)/3+200)*(i-4),HEIGHT/3+120);
            files[i].setSize(200,100);
            files[i].setFont(new Font("Rockwell", Font.BOLD, 25));
            add(files[i]);
        }
    }
    private JButton ContinueBack;
    private void addContinueBack(){
        ContinueBack=new JButton("<-Back");
        ContinueBack.addActionListener((e)->{
            Continue.setVisible(true);
            NewGameButton.setVisible(true);
            ContinueBack.setVisible(false);
            for(int i=0;i<8;i++){
                files[i].setVisible(false);
            }
        });
        ContinueBack.setLocation(50,50);
        ContinueBack.setSize(120, 60);
        ContinueBack.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(ContinueBack);
    }


    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGHT, HEIGHT / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        //add(button);
    }
    public JButton Swap;
    private void addSwapConfirmButton() {
        Swap = new JButton("Confirm Swap");
        Swap.addActionListener((e) -> {
            chessboardComponent.swapChess();

        });
        Swap.setLocation(HEIGHT, HEIGHT / 10 + 200);
        Swap.setSize(200, 60);
        Swap.setFont(new Font("Rockwell", Font.BOLD, 20));
        //add(button);
    }

    public JButton getSwap() {
        return Swap;
    }

    private void addSwapConfirmButton(int i) {
        Swap = new JButton("Confirm Swap");
        Swap.addActionListener((e) -> {
            chessboardComponent.swapChess();
            Swap.setEnabled(false);
        });
        Swap.setLocation(HEIGHT, HEIGHT / 10 + 200);
        Swap.setSize(200, 60);
        Swap.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(Swap);
        Swap.repaint();
    }

    private void addNextStepButton() {
        JButton button = new JButton("Next Step");
        button.addActionListener((e) -> chessboardComponent.nextStep());
        button.setLocation(HEIGHT, HEIGHT / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        //add(button);
    }
    private void addNextStepButton(int i) {
        JButton button = new JButton("Next Step");
        button.addActionListener((e) -> {
            chessboardComponent.nextStep();
            if(GameController.hasNull(chessboardComponent.getGameController().getModel())){
                Swap.setEnabled(false);
            }
            else if(!GameController.isValid(chessboardComponent.getGameController().getModel())) Swap.setEnabled(false);
            else Swap.setEnabled(true);
        });
        button.setLocation(HEIGHT, HEIGHT / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.repaint();
    }
//    private void addLoadButton() {
//        JButton button = new JButton("Load");
//        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//
//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
//    }


}
