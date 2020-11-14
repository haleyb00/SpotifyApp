import java.io.File; 
import java.io.IOException; 
import java.util.Scanner; 
  
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.DataLine.Info;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

/*
    To compile: javac SpotifyLikeApp.java
    To run: java SpotifyLikeApp
 */

// declares a class for the app
public class SpotifyLikeApp {

    // global variables for the app
    String status;
    Long position;
    static Clip audioClip ;
    static String[][] songs = {
    		{"Cement Lunch","Ava Luna","2002","Hip-Hop","Ava_Luna_-_02_-_Cement_Lunch.wav"},
    		{"Journey of King","Bisou","2004","Hip-Hop","Bisou_-_04_-_Journey_of_King.wav"},
    		{"Tanzen","Checkie Brown","2010","Hip-Hop","Checkie_Brown_-_10_-_Tanzen_CB_003.wav"},
    		{"Wirklich Wichtig","Checkie Brown","2011","Hip-Hop","Checkie_Brown_-_11_-_Wirklich_Wichtig_CB_27.wav"},
    		{"Vacaciones Salsa","Dee Yan Key","2010","Hip-Hop","Dee_Yan-Key_-_10_-_vacaciones_salsa.wav"},
    		{"El Preso Numero Nueve","Kathleen Martin","2002","Hip-Hop","Kathleen_Martin_-_02_-_El_Preso_Numero_Nueve.wav"},
    		{"Welcome","Kitkat Club","2002","Hip-Hop","Kitkat_Club_-_02_-_Welcome.wav"},
    		{"Burn it Down","Mid-Air Machine","2004","Hip-Hop","Mid-Air_Machine_-_Burn_It_Down.wav"},
    		{"Storybook","Scott Holmes","2002","Hip-Hop","Scott_Holmes_-_Storybook.wav"},
    		{"Zumbido","The Dubbstyle","2004","Hip-Hop","The_Dubbstyle_-_05_-_Zumbido.wav"}
    };
    static int choice = -1;
    

    // "main" makes this class a java app that can be executed
    public static void main(final String[] args) {

        // create a scanner for user input
        Scanner input = new Scanner(System.in);

        String userInput = "";
        while (!userInput.equals("q")) {

            menu();

            // get input
            userInput = input.nextLine();

            // accept upper or lower case commands
            userInput.toLowerCase();
            // do something
            handleMenu(userInput);

        }

        // close the scanner
        input.close();

    }


    /*
     * displays the menu for the app
     */
    public static void menu() {

        System.out.println("---- SpotifyLikeApp ----");
        System.out.println("[H]ome");
        System.out.println("[S]earch by title");
        System.out.println("[L]ibrary");
        System.out.println("[P]lay");
        System.out.println("[Q]uit");
        System.out.println("");

        System.out.print("Enter q to Quit:");

    }

    /*
     * handles the user input for the app
     */
    public static void handleMenu(String userInput) {

        switch(userInput) {
            case "h":
                System.out.println("-->Home<--");
            	if(choice >= 0) {
            		System.out.println("The selected song is " +songs[choice][0] + " by " + songs[choice][1]);
            	}
                System.out.println("");
                break;

            case "s":
                System.out.println("-->Search by title<--");
                search();
                break;

            case "l":
                System.out.println("-->Library<--");
                library();
                break;
                
            case "p":

                if(choice > -1 && choice < songs.length)
                {
                    System.out.println("-->Play<--");
                    play();
                }
                else
                	System.out.println("Go to \"Search by title\" or \"Library\" Pick a song!\n\n");
                break;

            case "q":
                System.out.println("-->Quit<--");
                break;

            default:
                break;
        }
        
        

    }
    
    public static void search() {
    	System.out.println("Please enter your search:");
    	Scanner input = new Scanner(System.in);
    	String query = input.next();
    	System.out.println();
    	for(int x = 0; x < songs.length; x++) {
    		if(songs[x][0].toLowerCase().contains(query.toLowerCase())){
        		System.out.println(x + ":");
        		System.out.println("Title:\t" + songs[x][0]);
        		System.out.println("Artist:\t" + songs[x][1]);
        		System.out.println("Year:\t" + songs[x][2]);
        		System.out.println("Genre:\t" + songs[x][3]);
        		System.out.println();
    		}
    	}
    }
    
    public static void library() {
        Scanner input = new Scanner(System.in);
        choice = songs.length + 1;
    	System.out.println("Pick a Song:\n");
    	for(int x = 0; x < songs.length; x++) {
    		System.out.println(x + ":");
    		System.out.println("Title:\t" + songs[x][0]);
    		System.out.println("Artist:\t" + songs[x][1]);
    		System.out.println("Year:\t" + songs[x][2]);
    		System.out.println("Genre:\t" + songs[x][3]);
    		System.out.println();
    	}

    	while(choice > songs.length - 1) {
        	System.out.println("Enter Valid Song Number:");
    		choice = input.nextInt();
    		System.out.println();
    	}
    	System.out.println("");
		System.out.println("The selected song is " +songs[choice][0] + " by " + songs[choice][1]);
		System.out.println("");
		System.out.println("");
    }

    /*
     * plays an audio file
     */
    public static void play() {

        // open the audio file
    	String songName = "./src/Music/" + songs[choice][4];
        final File file = new File(songName);

        try {
        	if(audioClip != null) {
        		if(audioClip.isOpen())
        			audioClip.close();
        	}
            // create clip 
            audioClip = AudioSystem.getClip();

            // get input stream
            final AudioInputStream in = getAudioInputStream(file);

            audioClip.open(in);
            audioClip.setMicrosecondPosition(0);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch(Exception e) {
            e.printStackTrace(); 
        }

    }


}

