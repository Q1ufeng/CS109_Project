package model;


import java.awt.*;

public class ChessPiece {
    // Diamond, Circle, ...
    private String name;

    private Color color;
    //加入ID来便于存档和读档
    private int id;

    public ChessPiece(String name)
    {
        this.name = name;
        this.color = Constant.colorMap.get(name);
        switch(this.getName())
        {
            case "💎":
                this.id=1;
                break;
            case"⚪":
                this.id=2;
                break;
            case"▲":
                this.id=3;
                break;
            case"🔶":
                this.id=4;
                break;
        }
    }
    public static ChessPiece pieceById(int id)
    {
        switch (id)
        {
            case 1:
                return(new ChessPiece("💎"));
            case 2:
                return(new ChessPiece("⚪"));
            case 3:
                return(new ChessPiece("▲"));
            case 4:
                return(new ChessPiece("🔶"));
        }
        return null;
    }


    public String getName() {
        return name;
    }

    public Color getColor(){return color;}

    //对棋子的equals进行重载操作
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ChessPiece)
            return this.getName().equals(((ChessPiece) obj).getName());
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
//一块棋子的实现