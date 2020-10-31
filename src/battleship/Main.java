package battleship;
import java.util.*;

public class Main {
    public static int[][] battleGroundPlayer1=new int[10][10];
    public static int[][] battleGroundPlayer2=new int[10][10];
    public static int[][] battleGroundFogPlayer1=new int[10][10];
    public static int[][] battleGroundFogPlayer2=new int[10][10];
    public static int currentPlayer=1;
    public static Map<String,List<Integer>> player1Coord=new HashMap<>();
    public static Map<String,List<Integer>> player2Coord=new HashMap<>();
    public static int NumberOfShipsSank1=0;
    public static int NumberOfShipsSank2=0;

    public static void main(String[] args) {
        // Write your code here
        Scanner scanner=new Scanner(System.in);

        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                battleGroundPlayer1[i][j]=0;
                battleGroundPlayer2[i][j]=0;
                battleGroundFogPlayer1[i][j]=0;
                battleGroundFogPlayer2[i][j]=0;
            }
        }

        System.out.println("Player 1, place your ships on the game field");
        System.out.println();
        printBattleGround(battleGroundPlayer1);
        System.out.println();
        DecidePositions(battleGroundPlayer1,5,"Aircraft Carrier");
        DecidePositions(battleGroundPlayer1,4,"Battleship");
        DecidePositions(battleGroundPlayer1,3,"Submarine");
        DecidePositions(battleGroundPlayer1,3,"Cruiser");
        DecidePositions(battleGroundPlayer1,2,"Destroyer");

        System.out.println("Press Enter and pass the move to another player");

        if(scanner.nextLine().isEmpty()){
            currentPlayer=2;
            System.out.println("Player 2, place your ships on the game field");
            System.out.println();
            printBattleGround(battleGroundPlayer2);
            System.out.println();
            DecidePositions(battleGroundPlayer2,5,"Aircraft Carrier");
            DecidePositions(battleGroundPlayer2,4,"Battleship");
            DecidePositions(battleGroundPlayer2,3,"Submarine");
            DecidePositions(battleGroundPlayer2,3,"Cruiser");
            DecidePositions(battleGroundPlayer2,2,"Destroyer");
            currentPlayer=1;

        }

        System.out.println("Press Enter and pass the move to another player");

        boolean endCondition=true;
        while(endCondition){
            if(scanner.nextLine().isEmpty()){
                printBattleGroundMap();
                System.out.println();
                System.out.println("Player "+currentPlayer+", it's your turn:");
                System.out.println();

                String hitCoord=scanner.nextLine();

                System.out.println();
                int x=convertCharToIndex(hitCoord.substring(0,1));
                int y=Integer.parseInt(hitCoord.substring(1))-1;

                if(validHitCoord(x,y)){
                    TakeShot(x,y);
                    System.out.println("Press Enter and pass the move to another player");
                    System.out.println();
                }else{
                    System.out.println("Error! Try again:");
                }
            }
            endCondition=GameNotEndCondition();
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    public static void printBattleGround(int[][] battleground){
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for(int i=0;i<10;i++){
            System.out.print((char)(i+65)+" ");
            for(int j=0;j<10;j++){
                if(battleground[i][j]==0){
                    System.out.print("~ ");
                }else if(battleground[i][j]==1){
                    System.out.print("O ");
                }else if(battleground[i][j]==2){
                    System.out.print("X ");
                }else{
                    System.out.print("M ");
                }

            }
            System.out.println();
        }
    }

    public static void printBattleGroundMap(){
        if(currentPlayer==1){
            printBattleGround(battleGroundFogPlayer2);
            System.out.println("---------------------");
            printBattleGround(battleGroundPlayer1);
        }else{
            printBattleGround(battleGroundFogPlayer1);
            System.out.println("---------------------");
            printBattleGround(battleGroundPlayer2);
        }

    }

    public static String PosValidation(int[][] battleground,int shipLength,int x1,int y1,int x2,int y2,String shipName){
        if(x1!=x2&&y1!=y2){
            return "Error! Wrong ship location! Try again:";
        }else if(x1==x2&&y1==y2){
            return "Error! Wrong ship location! Try again:";
        }else if(y1<0||y2<0||y1>9||y2>9){
            return "Error! Wrong ship location! Try again:";
        }else if(x1<0||x2<0||x1>9||x2>9){
            return "Error! Wrong ship location! Try again:";
        }
        else if(y1==y2&&Math.abs(x1-x2)+1!=shipLength){
            return "Error! Wrong length of the "+shipName+"! Try again:";
        }
        else if(x1==x2&&Math.abs(y1-y2)+1!=shipLength){
            return "Error! Wrong length of the "+shipName+"! Try again:";
        }
        else if(!isPositionOccupied(battleground,x1,y1,x2,y2)){
            return "Error! You placed it too close to another one. Try again:";
        }
        else{
            return "true";
        }
    }


    public static void placeShip(int[][] battleGround,int x1,int y1,int x2,int y2){
        if(x1==x2){
            if(y1<y2){
                for(int i=y1;i<=y2;i++){
                    battleGround[x1][i]=1;
                }
            }else{
                for(int i=y2;i<=y1;i++){
                    battleGround[x1][i]=1;
                }
            }

        }else{
            if(x1<x2){
                for(int i=x1;i<=x2;i++){
                    battleGround[i][y1]=1;
                }
            }else{
                for(int i=x2;i<=x1;i++){
                    battleGround[i][y1]=1;
                }
            }
        }
    }

    public static boolean isPositionOccupied(int[][] battleGround,int x1,int y1,int x2,int y2){

        if(x1==x2){
            if(y1<y2){
                for(int i=x1-1;i<=x1+1;i++){
                    for(int j=y1-1;j<=y2+1;j++){
                        if(i>=0&&j>=0&&i<=9&&j<=9){
                            if(battleGround[i][j]==1){
                                return false;
                            }
                        }
                    }
                }
            }else{
                for(int i=x1-1;i<=x1+1;i++){
                    for(int j=y2-1;j<=y1+1;j++){
                        if(i>=0&&j>=0&&i<=9&&j<=9){
                            if(battleGround[i][j]==1){
                                return false;
                            }
                        }
                    }
                }
            }

        }else{
            if(x1<x2){
                for(int i=x1-1;i<=x2+1;i++){
                    for(int j=y1-1;j<=y1+1;j++){
                        if(i>=0&&j>=0&&i<=9&&j<=9){
                            if(battleGround[i][j]==1){
                                return false;
                            }
                        }
                    }
                }
            }else{
                for(int i=x2-1;i<=x1+1;i++){
                    for(int j=y1-1;j<=y1+1;j++){
                        if(i>=0&&j>=0&&i<=9&&j<=9){
                            if(battleGround[i][j]==1){
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;

    }

    public static int convertCharToIndex(String s){
        int val=-1;
        switch (s){
            case "A":val=0;
            break;
            case "B":val=1;
            break;
            case "C":val=2;
            break;
            case "D":val=3;
            break;
            case "E":val=4;
            break;
            case "F":val=5;
            break;
            case "G":val=6;
            break;
            case "H":val=7;
            break;
            case "I":val=8;
            break;
            case "J":val=9;
            break;
            default:break;
        }
        return val;
    }

    public static boolean validHitCoord(int x , int y){
        if(x<0||x>9||y<0||y>9){
            return false;
        }
        return true;
    }

    public static void TakeShot(int x,int y){
        if(currentPlayer==1){
            if(battleGroundPlayer2[x][y]==1||battleGroundPlayer2[x][y]==2){
                battleGroundPlayer2[x][y]=2;
                battleGroundFogPlayer2[x][y]=2;
                if(hasShipSank(player2Coord,battleGroundPlayer2)){
                    System.out.println("You sank a ship!");
                }else{
                    System.out.println("You hit a ship!");
                }

            }else{
                battleGroundPlayer2[x][y]=3;
                battleGroundFogPlayer2[x][y]=3;
                System.out.println("You missed!");
            }
            currentPlayer=2;
        }else{
            if(battleGroundPlayer1[x][y]==1||battleGroundPlayer1[x][y]==2){
                battleGroundPlayer1[x][y]=2;
                battleGroundFogPlayer1[x][y]=2;
                if(hasShipSank(player1Coord,battleGroundPlayer1)){
                    System.out.println("You sank a ship!");
                }else{
                    System.out.println("You hit a ship!");
                }

            }else{
                battleGroundPlayer1[x][y]=3;
                battleGroundFogPlayer1[x][y]=3;
                System.out.println("You missed!");
            }
            currentPlayer=1;
        }

    }

    public static boolean GameNotEndCondition(){
        int hitCountPlayer1=0;
        int hitCountPlayer2=0;
        for(int i=0;i<=9;i++){
            for(int j=0;j<=9;j++){
                if(battleGroundPlayer1[i][j]==2){
                    hitCountPlayer1++;
                }
                if(battleGroundPlayer2[i][j]==2){
                    hitCountPlayer2++;
                }
            }
        }
        if(hitCountPlayer1==17||hitCountPlayer2==17){
            return false;
        }
        return true;
    }

    public static void DecidePositions(int[][] battleGround,int shipLength,String shipName){

        System.out.println("Enter the coordinates of the "+shipName+ " ("+shipLength+" cells):");
        Scanner scanner =new Scanner(System.in);
        System.out.println();
        boolean isPositionValid=false;
        while (!isPositionValid){
            String pos1=scanner.next();
            String pos2=scanner.next();
            System.out.println();
            int x1=convertCharToIndex(pos1.substring(0,1));
            int y1=Integer.parseInt(pos1.substring(1))-1;
            int x2=convertCharToIndex(pos2.substring(0,1));
            int y2=Integer.parseInt(pos2.substring(1))-1;
            ArrayList<Integer> coords=new ArrayList<>();
            coords.add(x1);
            coords.add(y1);
            coords.add(x2);
            coords.add(y2);
                String validationResult=PosValidation(battleGround,shipLength,x1,y1,x2,y2,shipName);
                if(validationResult.equals("true")){
                    placeShip(battleGround,x1,y1,x2,y2);
                    if(currentPlayer==1){
                        player1Coord.put(shipName,coords);
                    }else{
                        player2Coord.put(shipName,coords);
                    }
                    printBattleGround(battleGround);
                    System.out.println();
                    isPositionValid=true;
                }else{
                    System.out.println(validationResult);
                    System.out.println();
                }

        }

    }

    public static boolean hasShipSank(Map<String,List<Integer>> coordinates,int[][] battleGround){
        int count=0;
        for (List<Integer> Lin:coordinates.values()
             ) {
             int x1=Lin.get(0);
             int y1=Lin.get(1);
             int x2=Lin.get(2);
             int y2=Lin.get(3);

            boolean truth=true;

            if(x1==x2){
                if(y1<y2){
                    for(int i=y1;i<=y2;i++){
                        if(battleGround[x1][i]==1){
                            truth=false;
                        }
                    }
                }else{
                    for(int i=y2;i<=y1;i++){
                        if(battleGround[x1][i]==1){
                            truth=false;
                        }
                    }
                }

            }else{
                if(x1<x2){
                    for(int i=x1;i<=x2;i++){
                        if(battleGround[i][y1]==1){
                            truth=false;
                        }
                    }
                }else{
                    for(int i=x2;i<=x1;i++){
                        if(battleGround[i][y1]==1){
                            truth=false;
                        }
                    }
                }
            }

            if(truth==true){
                count++;
            }
        }

        if(currentPlayer==1){
            if(count>NumberOfShipsSank1){
                NumberOfShipsSank1++;
                return true;
            }
        }else{
            if(count>NumberOfShipsSank2){
                NumberOfShipsSank2++;
                return true;
            }
        }
        return false;
    }
}
