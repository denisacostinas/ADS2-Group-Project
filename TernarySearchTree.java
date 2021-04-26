package groupAssignment;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** class Node **/
class Node {
	
    char data;
    boolean isEnd;
    Node left, middle, right; 

    /** Constructor **/
    public Node(char data)
    {
        this.data = data;
        this.isEnd = false;
        this.left = null;
        this.middle = null;
        this.right = null;

    }        

}

/** class TernarySearchTree **/
class TernarySearchTree
{
    public Node root;
    public ArrayList<String> al; //a list holding the search result, used in traverse function

    /** Constructor **/
    public TernarySearchTree()
    {
        root = null;
    }
    
    /** function to insert for a word **/
    public void insert(String word)
    {
        root = insert(root, word.toCharArray(), 0);
    }

    /** function to insert for a word **/
    public Node insert(Node r, char[] word, int ptr)
    {
        if (r == null)  r = new Node(word[ptr]);

        if (word[ptr] < r.data)   
    	{
        	r.left = insert(r.left, word, ptr);
    	}
        else if (word[ptr] > r.data)
        {
            r.right = insert(r.right, word, ptr);
        }
        else
        {
            if (ptr + 1 < word.length)
                r.middle = insert(r.middle, word, ptr + 1);
            else
                r.isEnd = true;
        }
        return r;

    }


    /** function to search for a word **/
    public ArrayList<String> search(String word)
    {
        return search(root, word.toCharArray(), 0);
    }

    /** function to search for a word **/
    public ArrayList<String> search(Node r, char[] word, int ptr)
    {
        if (r == null) return new ArrayList<String>();

        if (word[ptr] < r.data)
        {
            return search(r.left, word, ptr);
        }
        else if (word[ptr] > r.data)
        {
            return search(r.right, word, ptr);
        }
        else
        {
            if (r.isEnd && ptr == word.length - 1)
            {
            	al = new ArrayList<String>();
            	String s = new String(word);
            	al.add(s);
            	return al;
            }
            else if (ptr == word.length - 1)
            {
            	al = new ArrayList<String>();
            	String s = new String(word);
            	traverse(r.middle,s);
            	return al;
            }
            else
            {
                return search(r.middle, word, ptr + 1);
            }
        }        
    }
    

    /** function to traverse tree **/
    public void traverse(Node r, String str)
    {
        if (r != null)
        {
            traverse(r.left, str);

            str = str + r.data;

            if (r.isEnd) al.add(str);

            traverse(r.middle, str);

            str = str.substring(0, str.length() - 1);

            traverse(r.right, str);
            
        }
    }
    
    
    public static void main(String[] args) throws IOException

    {

    	String [] bannedWords= {"WB","NB","SB","EB","FLAGSTOP"};
        Scanner scan = new Scanner(System.in);

        /* Creating object of TernarySearchTree */
        TernarySearchTree tst = new TernarySearchTree(); 

        System.out.println("Ternary Search Tree where the following heading information will be shown for each bus stop:\n"); 
        System.out.println("Stop name - stop ID - stop code - top_desc - stop_lat - stop_lon - zone_id - stop_url - location_type - parent_station"); 

        //Get the Data && insert to the tree
        File file = new File("/Users/denisacostinas/Desktop/inputs ADS /stops.txt");
        @SuppressWarnings("resource")
		Scanner myReader = new Scanner(file);    // reading in each line - a reader - allows you to read file
               
        
        int lineCounter = 0;                // counts how many lines are read 
        while(myReader.hasNextLine()) {       // checking to see if line exists 
        	
        	String line = myReader.nextLine();    //reads the next line into the String line (one at a time)
       	 lineCounter++;
       	 

       	 
        	if(lineCounter != 1) {    // if the line is not the first line then we add to tree 
        		
        		String[] array = line.split(","); 
        		array[2]=array[2].toUpperCase();
    			String[] array2=array[2].split(" ");
    			if(Arrays.asList(bannedWords).contains(array2[0]))
    			{
    				String tmp=array2[0];
    				for(int i=0;i<array2.length-1;i++)
    				{
    					array2[i]=array2[i+1];
    				}
    				array2[array2.length-1]=tmp;
    				array[2]=String.join(" ", array2);
    			}
    			String tmp=array[0];
    			array[0]=array[2];
    			array[2]=array[1];
    			array[1]=tmp;
    			tmp=String.join(" - ", array);
    			tst.insert(tmp); 

    		}
        	   
        	}
        
        
       
        char ch;

        /*  Perform tree operations  */
        do    
        {         
            System.out.println("Enter bus stop to search");
            ArrayList<String> results=tst.search( scan.nextLine().toUpperCase() );
            System.out.println("Search result : "); //using nextLine() and not next() to take the whole line , next() takes only the first word
            for(String result : results)
			{
				System.out.println(result);
			}
            
        

            System.out.println("\nDo you want to continue (Type y or n) \n");

            ch = scan.next().charAt(0);                        
            scan.nextLine(); //just to skip the Enter
            
        } while (ch == 'Y'|| ch == 'y');        

    }

}