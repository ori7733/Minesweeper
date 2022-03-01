
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.FlowLayout;
import java.awt.Font;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class FirstClass implements ActionListener , MouseListener{
    private int lineY;
    private int lineX;
    private int[][] gameBoard;
    private int[][] dataBoard;
    private int remainingMines;
    private final int boom=9;
    private int numToDiscovered;
    private int level;
    private JFrame frame;
    private JButton[][] btnArr;
    private boolean firstStep=true;
    private JMenuBar mb;
    private JMenu file=new JMenu(getTimerSt()+ "  mines left: "+remainingMines);

    
 
    int elapsedTime = 0;
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    boolean started = false;
    String seconds_string = String.format("%02d", seconds);
    String minutes_string = String.format("%02d", minutes);
    String hours_string = String.format("%02d", hours);

    Timer timer = new Timer(1000, new ActionListener() {
        

        public void actionPerformed(ActionEvent e) {

            frame.setTitle(getTimerSt()+ "  mines : "+remainingMines);
            
            //frame.setMenuBar(getTimerSt()+ "  mines : "+remainingMines);
            /*
            mb=new JMenuBar();
            frame.setJMenuBar(mb);
            JMenu file=new JMenu(getTimerSt()+ "  mines : "+remainingMines);
            file.setDelay(1);
            file.setBackground(Color.GREEN);
            mb.add(file);
            */
                                        
        }

    });
    private String getTimerSt(){
        elapsedTime = elapsedTime + 1000;
            hours = (elapsedTime / 3600000);
            minutes = (elapsedTime / 60000) % 60;
            seconds = (elapsedTime / 1000) % 60;
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            hours_string = String.format("%02d", hours);
            return (hours_string + ":" + minutes_string + ":" + seconds_string);
            
    }

    public FirstClass(int X,int Y,int level){
        this.lineY=X;
        this.lineX=Y;
        this.gameBoard=new int[lineY][lineX];
        this.dataBoard=new int[lineY][lineX];
        this.btnArr= new JButton[lineY][lineX];
        this.level=setLevel(level);
     //   stopwatch=new Stopwatch();
        newGame(this.level);
        init();
    }
    //set the level of the game- the hiher thr number the bigger the num of the mins
    private int setLevel(int level){
        switch(level)
        {
            case(1):
                return 10;
            case(2):
                return 8;
            case(3):
                return 6;
            case(4):
                return 3;
            default:
                return 8;
        }
    }
    // creat the Jframe, butten size 
    void init(){
         //  final int SHURA=10;
         //Image photo=new ImageIcon(this.getClass().getResource("C:\Users\melli\Documents\NetBeansProjects\Minesweeper\src\main\java\image\numbers\eight.png")).getImage();
         //ImageIcon pngt=new ImageIcon("C:\\Users\\melli\\Documents\\NetBeansProjects\\Minesweeper\\src\\main\\java\\image\\numbers\\eight.png")                    
        this.frame = new JFrame();
        this.frame.setLayout(new GridLayout(lineY,0));
        for (int i = 0; i < lineY*lineX; i++) {
            //JButton btn=new JButton((Icon) photo);
            JButton btn = new JButton("   ");
            //btn.setActionCommand(""+posY);
            //btn.addActionListener(this);
            btn.addMouseListener(this);
            btn.setFont(new Font("Agency FB", Font.PLAIN, 40));
            btn.setName(""+i);     
            this.frame.add(btn);
            this.btnArr[i/lineX][i%lineX] = btn;      
        }
        this.frame.setSize(1200,1200);
        this.frame.setVisible(true);
        this.btnArr[0][0].setBackground(Color.BLUE);
        this.btnArr[0][0].setBackground(new JButton().getBackground());
        //mb=new JMenuBar();
        //frame.setJMenuBar(mb);        
        //file.setBackground(Color.GREEN);
        //mb.add(file);
    }  
    
    
    //return an random numnber 1-50 
    private int myRND(){
    int num = (int)(Math.random() *50); //returns an integer 
    return num;
}

    private void resetBoard(int[][] board, int num){
        for (int i = 0; i < lineY; i++) 
            for (int j = 0; j < lineX; j++) {
                board[i][j]=num;
            }
    }
  
    
    //
    private void newGame(int level){
        resetBoard(this.dataBoard,-1);
        resetBoard(this.gameBoard,0);
        int minesAmount=(lineY*lineX/level);
        this.numToDiscovered=lineY*lineX-minesAmount;
        this.remainingMines=minesAmount;
        throwingBombs(minesAmount);
        for (int i = 0; i < lineY; i++) 
            for (int j = 0; j < lineX; j++)
                if(this.gameBoard[i][j]!=boom)
                setNumbers(i,j);
    }
    
    private void throwingBombs(int minesAmount)
    {
        while (minesAmount>0) {            
        for (int i = 0; i < lineY; i++) {
            for (int j = 0; j < lineX; j++) 
                if(minesAmount>0&&myRND()==1&&this.gameBoard[i][j]!=boom)
                {
                    this.gameBoard[i][j]=boom;
                    minesAmount--;
                }
            }
        }
    }
    
    private void printBoard(int[][] board){
        for (int i = 0; i < lineY; i++) 
            for (int j = 0; j < lineX; j++) {
                System.out.print(" "+board[i][j]);
                if(j==lineX-1)
                    System.out.println();
            }
    } 
    
    
    private void setNumbers(int y,int x){
        int count=0;
        for (int i = y-1; i < y+2; i++) 
            for (int j = x-1; j < x+2; j++){
                if (i<0||i>lineY-1||j<0||j>lineX-1 )               
                continue;
                else{
                    if(this.gameBoard[i][j]==this.boom)
                     count++;
                }
                this.gameBoard[y][x]=count;
            }
    }

    /*
    private void copyArr() {
        for (int posY = 0; posY < lineY; posY++) 
            for (int posX = 0; posX < lineX; posX++)
                this.gameBoard[posY][posX]=this.gameBoard[posY][posX];
    }
*/
    
    private void recommendStep(){
        for (int posY = 0; posY < lineY; posY++) 
            for (int posX = 0; posX < lineX; posX++) {
                if(this.dataBoard[posY][posX]!=-1&&this.dataBoard[posY][posX]!=-2)
                {
                    recommendFUCK(posY,posX,this.dataBoard[posY][posX]);
                }
            }
        
    }
    private void recommendFUCK(int posY,int posX,int number){
        int count=0;
        int num=this.dataBoard[posY][posX];
        for (int i = posY-1; i < posY+2; i++) 
            for (int j = posX-1; j < posX+2; j++){
                if ((i<0||i>lineY-1||j<0||j>lineX-1)||(posY==i&&posX==j||dataBoard[i][j]!=-1))          
                    continue;
                if(this.dataBoard[i][j]==-2)
                    num--;
                count++;
        }
        if(dataBoard[posY][posX]==count)
        for (int i = posY-1; i < posY+2; i++){
            for (int j = posX-1; j < posX+2; j++){
                if ((i<0||i>lineY-1||j<0||j>lineX-1)||(posY==i&&posX==j||dataBoard[i][j]!=-1||this.btnArr[i][j].getBackground()==Color.ORANGE))          
                    continue;
                this.btnArr[i][j].setBackground(Color.ORANGE);
                //return;
            }
        }
        
            
    }
    
    
        
    private void endGame(boolean iswin) {
        int level;
      //  stopwatch.stop();
      timer.stop();
        String winOrlose;
        if(iswin)
            winOrlose="Good job! Want to play again?";
        else 
            winOrlose="Maybe next time. Want to play again?";
        winOrlose=JOptionPane.showInputDialog(winOrlose);
        if (winOrlose.equals("no")){
            //printBoard(this.dataBoard);
            JOptionPane.showMessageDialog(null,"to bad!");
      //      stopwatch.close();
            this.frame.setVisible(false);
            this.frame.dispose();
            return;
        }
        String levelTemp;
        levelTemp=JOptionPane.showInputDialog("then let's play again!! What level of difficulty do you want?");
        level = Integer.parseInt(levelTemp);
      //  stopwatch.close();
        JOptionPane.showMessageDialog(null,"let's go!");
        this.frame.setVisible(false);
        FirstClass a=new FirstClass(lineY,lineX,level);
    }
    
    private void zeroDetection(int y,int x){
         for (int i = y-1; i < y+2; i++) 
            for (int j = x-1; j < x+2; j++){
                if ((i<0||i>lineY-1||j<0||j>lineX-1)||(y==i&&x==j))          
                continue;
                    if(this.gameBoard[i][j]==0&&this.dataBoard[i][j]==-1){
                        this.dataBoard[i][j]=0;
                        this.btnArr[i][j].setBackground(Color.WHITE);
                        this.btnArr[i][j].setText(" ");
                        this.btnArr[i][j].getModel().setPressed(true); 
                        this.numToDiscovered--;
                        zeroDetection(i, j);
                    }
                    if(this.dataBoard[i][j]==-1){
                        this.dataBoard[i][j]=gameBoard[i][j];
                        this.btnArr[i][j].setBackground(Color.WHITE);
                        this.btnArr[i][j].setText(""+gameBoard[i][j]);
                        this.btnArr[i][j].getModel().setPressed(true);
                        this.numToDiscovered--;
                    }
                }
    }
    
   
    @Override
    public void actionPerformed(ActionEvent e) {//////////////////////////////////////////////////////////////////////////////////////////////
        int posX,posY,i;
        i= Integer.parseInt( e.getActionCommand());
        posY = i/lineX;
        posX= i%lineX;
        //System.out.println(" butten :"+posY+","+posY);
        this.btnArr[posY][posX].setBackground(Color.red);
        this.btnArr[posY][posX].setText(""+2);
        
         if (2 == 3) { //if right click
                this.btnArr[posY][posX].setText("F");
                this.btnArr[posY][posX].getModel().setPressed(false);
                //button.setEnabled(true);
            } else {
                this.btnArr[posY][posX].setText("X");
                this.btnArr[posY][posX].getModel().setPressed(true);
                //button.setEnabled(false);
            }
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private void leftClick(int posY,int posX){
        while (this.firstStep&&this.gameBoard[posY][posX]==boom){     
            newGame(level);
        }
        timer.start();
        this.firstStep=false;
        //System.out.println(getTimerSt());
        if(this.gameBoard[posY][posX]==boom){
            this.btnArr[posY][posX].setBackground(Color.red);
            this.btnArr[posY][posX].setText("BOOM!");
            this.btnArr[posY][posX].getModel().setPressed(true);
            this.dataBoard[posY][posX]=9;
            endGame(false);
            this.frame.setVisible(false);
            }
        else{
            if(this.dataBoard[posY][posX]==-1)
                this.numToDiscovered--;
            this.btnArr[posY][posX].setBackground(Color.WHITE);
            this.btnArr[posY][posX].setText(""+this.gameBoard[posY][posX]);
            this.btnArr[posY][posX].getModel().setPressed(true);
            this.dataBoard[posY][posX]=this.gameBoard[posY][posX];
            if(this.gameBoard[posY][posX]==0)
                zeroDetection(posY,posX);
            if(this.numToDiscovered==0){
                endGame(true);
                this.frame.setVisible(false);
            }
                //Image photo=new ImageIcon(this.getClass().getResource("C:\\Users\\melli\\Documents\\NetBeansProjects\\Minesweeper\\src\\main\\java\\image\\numbers\\one.png")).getImage();
                //this.btnArr[posY][posY].setIcon((Icon) photo);
                recommendStep();

        }
        

    }

    
    private void rightClick(int posY,int posX){
        
        
        if(this.btnArr[posY][posX].getBackground()==Color.ORANGE)
            this.btnArr[posY][posX].setBackground(new JButton().getBackground());
        //System.out.println(getTimerSt());
        if (this.dataBoard[posY][posX]!=-2&&remainingMines>0){
            this.btnArr[posY][posX].setText("F");
            this.dataBoard[posY][posX]=-2;
            this.remainingMines--;
        }
            else if (this.dataBoard[posY][posX]==-2){
                this.btnArr[posY][posX].setText(" ");
                this.dataBoard[posY][posX]=-1;
                this.remainingMines++;
            }
        if(this.remainingMines==0){
            boolean winnerBool=true;
            //printBoard(dataBoard);
            for (int i = 0; i < lineY; i++){
                for (int j = 0; j < lineX; j++){
                    if(this.dataBoard[i][j]==-2&&this.gameBoard[i][j]!=boom){
                        winnerBool=false;
                        break;
                    }
                }
                if(!winnerBool)
                    break;
            }
            //mb.removeAll();
            //JMenu file=new JMenu(getTimerSt()+ "  mines : "+remainingMines);
            //mb.add(file);
        if(winnerBool)
            endGame(true);
        }
        this.btnArr[posY][posX].getModel().setPressed(false);
        this.btnArr[posY][posX].setEnabled(true);
        //System.out.println(" \n remainingMines "+remainingMines);
        //System.out.println(" \n numDiscovered "+numToDiscovered);
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //mb.add(new JMenu("Test"));
        //mb.removeAll();
        
        //JMenu file=new JMenu(getTimerSt()+ "  mines : "+remainingMines);
        //mb.add(file);
        boolean firstStep=false;
        //System.out.println("FirstClass.mouseClicked()" + e.getButton() );
        int posX,posY,i=0;
        i= Integer.parseInt( e.getComponent().getName());
        posY = i/lineX;
        posX= i%lineX;
        if (e.getButton() == 3&&(this.dataBoard[posY][posX]==-1||this.dataBoard[posY][posX]==-2))// if right click on unpresed btn
                    rightClick(posY, posX);
                else  if(this.dataBoard[posY][posX]==-1)// if left click
                    leftClick(posY, posX);
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    

    
}