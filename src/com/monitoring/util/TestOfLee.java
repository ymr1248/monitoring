package com.monitoring.util;
import java.lang.reflect.*;
/**
 * Created by Administrator on 2018-03-12.
 */
public class TestOfLee {
    static final int MAX_NUM = 8;

    int chessBoard[][] = new int[MAX_NUM][MAX_NUM];

    boolean check(int x,int y){
        for(int i = 0;i < y;i++){
            if (chessBoard[x][i] == 1){
                return false;
            }

            if(x - i - 1 >= 0 && chessBoard[x - i - 1][y - i - 1] == 1){
                return false;
            }

            if (x + i + 1 < MAX_NUM && chessBoard[x + i + 1][y - i - 1] == 1) {
                return false;
            }
        }
        return true;
    }

    boolean settleQ(int y){
        if (y == MAX_NUM){
            return true;
        }

        for(int i = 0; i < MAX_NUM; i++){
            for(int x = 0;x < MAX_NUM; x++){
                chessBoard[x][y] = 0;
            }

            if (check(i,y)){
                chessBoard[i][y] = 1;

                if(settleQ(y + 1)){
                    return true;
                }
            }
        }
        return false;
    }

    void printC(){
        for(int i = 0; i < MAX_NUM;i++){
            for(int j = 0;j < MAX_NUM; j++){
                System.out.print(chessBoard[i][j]);
            }
            System.out.println();
        }

    }

   public static void main(String args[]){
       TestOfLee queen = new TestOfLee();
       queen.settleQ(0);
       queen.printC();

   }
}
