package com.danieltalik.fullStackApp.Misc;

import java.sql.Array;

public class Katas {
        public static String print(int n) {
            StringBuilder build = new StringBuilder();
            if(n<=0 || n % 2 ==0 ){
                return null;
            }
            else{
                for(int i = 0; i < n; i+=2){
                    if(i < n-3){
                        build.append("  ");
                    }
                    else if(i < n-2){
                        build.append(" ");
                    }

                   for(int j = 0; j <= i; j++){
                       build.append("*");
                   }
                    build.append("\n");
                }
                for(int i = n-1; i > 0; i-=2) {
                    if(i+1 < n){
                        build.append("  ");
                    }
                    else if(i < n){
                        build.append(" ");
                    }
                    for (int j = i-1; j > 0; j--) {
                        build.append("*");
                    }
                    build.append("\n");
                }

            }
            return build.toString();
        }
}
