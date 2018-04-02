package pack_TI;

import pack_TI.JImageViewer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnNewButton;
	private JButton btnNewButton2;
	private JButton codage;
	private JButton décodage;
	private static int réponse=0;
	static private JTextArea zone_de_texte;
	private JFileChooser fc;
	private BufferedImage bufImgSource=null;
	private BufferedImage bufImgInter=null;
	private BufferedImage bufImgDest=null;
	private JImageViewer imgSourceViewer=null;
	private JImageViewer imgDestViewer=null;
	static private JSlider nombre_bit=null;
	static private JSlider mode_fonctionnement=null;
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TI frame = new TI();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 1020, 475);
		setTitle("Traitement d'images");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_0 = new JPanel();
		panel_0.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_0, BorderLayout.NORTH);
		
		btnNewButton = new JButton("Ouvrir une image afin de la traiter");
		btnNewButton.addActionListener(this);
		panel_0.add(btnNewButton);
		
		codage  = new JButton("Coder l'image");
		codage.addActionListener(this);
		panel_0.add(codage);
		
		décodage = new JButton("Décoder l'image");
		décodage.addActionListener(this);
		panel_0.add(décodage);
				
		final int nbits_max=4;
		final int nbits_min=1;
		final int nbits_ini=2;
		nombre_bit=new JSlider(JSlider.HORIZONTAL, nbits_min, nbits_max, nbits_ini);
		nombre_bit.setMajorTickSpacing(1);
		nombre_bit.setMinorTickSpacing(1);
		nombre_bit.setPaintTicks(true);
		nombre_bit.setPaintLabels(true);
		panel_0.add(nombre_bit);
		
		JLabel 	sliderLabel= new JLabel("bit(s) de (dé)codage");
		//sliderLabel.setLabelFor(nombre_bit);
		panel_0.add(sliderLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setLayout(new BorderLayout(0, 0));
		
		String l="Saisisser votre texte ici:";
		zone_de_texte = new JTextArea(l,5,1);
		JScrollPane scroll1 = new JScrollPane(zone_de_texte);
		contentPane.add(scroll1, BorderLayout.SOUTH);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setLayout(new BorderLayout(0, 0));
	
		JPanel panel_inter = new JPanel();
		panel_inter.setLayout(new GridLayout(1, 2));
		panel_inter.add(panel_1);
		panel_inter.add(panel_2);
		
		contentPane.add(panel_inter, BorderLayout.CENTER);
		
		JLabel lblImgSource = new JLabel(" Source");
		imgSourceViewer=new JImageViewer();
		panel_1.add(imgSourceViewer,BorderLayout.CENTER);
		panel_1.add(lblImgSource,BorderLayout.SOUTH);
		JLabel lblImgDest = new JLabel(" Destination");
		imgDestViewer=new JImageViewer();
		panel_2.add(imgDestViewer,BorderLayout.CENTER);
		panel_2.add(lblImgDest,BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnNewButton){
	        fc = new JFileChooser("./");
			int returnVal = fc.showDialog(null,"Open Image");
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            try {
					bufImgSource = ImageIO.read(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
	            
	            
	            
	            imgSourceViewer.setImage(bufImgSource);
	            repaint();
	        }
	        
		}
		if (arg0.getSource()== décodage){
			
			fc = new JFileChooser("./");
			int returnVal = fc.showDialog(null,"Open Image");
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	           
	            try {
					bufImgSource = ImageIO.read(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
	           
    		}
			 String[] decodage = {"Bit de poids fort", "Bit de poids faible"};
				réponse=JOptionPane.showOptionDialog(null,
						"Choisisez le sens de décodage,", 
						"Sens",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, 
						null,
						decodage,
						decodage [1]);
				System.out.println(réponse);
				
				if (réponse==1)
					bufImgDest=Décoder_Image_bonsens(bufImgSource);
				if (réponse==0)
					bufImgDest=Décoder_Image_autresens(bufImgSource);
				
				
				imgSourceViewer.setImage(bufImgSource);
	            imgDestViewer.setImage(bufImgDest);
	            repaint();
        }
		if (arg0.getSource()==codage){
		
			 String[] decodage = {"Bit de poids fort", "Bit de poids faible"};
				réponse=JOptionPane.showOptionDialog(null,
						"Choisisez le sens de codage,", 
						"Sens",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, 
						null,
						decodage,
						decodage [1]);
				System.out.println(réponse);
				
				if (réponse==1)
					bufImgDest=coder_Image_bonsens(bufImgSource);
				if (réponse==0)
					bufImgDest=coder_Image_autresens(bufImgSource);
				
				
				imgSourceViewer.setImage(bufImgSource);
	            imgDestViewer.setImage(bufImgDest);
	            repaint();

	            try {
	    			ImageIO.write(bufImgDest, "png", new File("./Image_traitee.png"));
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	        }
	        
		}
		 
	
	
	/**
	 * @param source : l'image à traiter
	 * @return l'image traitée
	 */
	
	
	
	//----------------------------------------Sens inverse Codage-----------------------------------------//
	private static BufferedImage coder_Image_autresens(BufferedImage source) {
		int w = source.getWidth();
		int h = source.getHeight();
		int[] rgbs = new int[w*h]; 
		rgbs=source.getRGB(0,0,w,h,null,0,w);
		int[] newrgbs=new int[rgbs.length];
		int bit = 0;
		int c=0;
		int i=0;
		int nbits=nombre_bit.getValue();;
		int numcomp = 3;
		int numbits = 8-nbits;
		int x=0;
		int y=0;
		int masque=0;
		String Phrase = zone_de_texte.getText()+" !3wZ ";
		int taille_phrase= Phrase.length();
		int comp = rgbs[y*w+x];
		 
		for (int ybis=0;ybis<=h-1;ybis++){
		 for (int xbis=0;xbis<=w-1;xbis++){
					newrgbs [ybis*w+xbis]=rgbs[ybis*w+xbis];
			}
		}
		
			for ( c=0; c<taille_phrase; c++ ){
				
				int carac = Phrase.charAt(c);
				
				 for(i=0;i<=7;i++) {
					 
					 
					 bit= (carac & (1<<(7-i)))>>(7-i);
				 	
				 	 
				 	if (bit==1){
				 	masque = 1<<(8*numcomp-1-numbits);
				 	comp = comp | masque;
				 	}
				 	
				 	else{
				 	masque = ~(1<<(8*numcomp-1-numbits));
				 	comp = comp & masque;
				 	}
				 	
					 
					 if (numbits==7){
						 numbits = 8 - nbits;
						
						 if (numcomp==1){
							 numcomp=3;
							 newrgbs[y*w+x]=comp;
							 x++;
					                
							 		if (x==w){
					                	y++;
					                	x=0;
					                }
				              comp = rgbs[y*w+x];
						 	}
						 else {
							 numcomp --;
						 }
					 }
					 else {
						 numbits ++;
					 }
				 }
				}
			
			
			 if (numcomp!=1){
				 newrgbs[y*w+x]=comp;
			 }
			 

		BufferedImage dest = new BufferedImage(w,h,source.getType());
		dest.setRGB(0,0,w,h,newrgbs,0,w);
		return dest;
	}
	
	
	//--------------------------------------------Bon sens Codage--------------------------------//
	private static BufferedImage coder_Image_bonsens(BufferedImage source) {
		int w = source.getWidth();
		int h = source.getHeight();
		int[] rgbs = new int[w*h]; 
		rgbs=source.getRGB(0,0,w,h,null,0,w);
		int[] newrgbs=new int[rgbs.length];
		int bit = 0;
		int c=0;
		int i=0;
		int nbits=nombre_bit.getValue();
		int numcomp = 0;
		int numbits = 0;
		int x=0;
		int y=0;
		int masque=0;		
		//String Phrase ="Bjour" +" STOP";
		String Phrase = zone_de_texte.getText()+" !3wZ ";
		int taille_phrase= Phrase.length();
		int comp = rgbs[y*w+x];
		System.out.println("Phrase");
		

		for (int ybis=0;ybis<=h-1;ybis++){
		 for (int xbis=0;xbis<=w-1;xbis++){
					newrgbs [ybis*w+xbis]=rgbs[ybis*w+xbis];
			}
		}
		
			for ( c=0; c<taille_phrase; c++ ){
				
				int carac = Phrase.charAt(c);
				
				 for(i=0;i<=7;i++) {
					 
					 
					 bit= (carac & (1<<(7-i)))>>(7-i);
				 	
				 	 
				 	if (bit==1){
				 	masque = 1<<(8*numcomp+numbits);
				 	comp = comp | masque;
				 	}
				 	
				 	else{
				 	masque = ~(1<<(8*numcomp+numbits));
				 	comp = comp & masque;
				 	}
				 	
					 
					 if (numbits==nbits-1){
						 numbits = 0;
						
						 if (numcomp==2){
							 numcomp=0;
							 newrgbs[y*w+x]=comp;
							 //System.out.println(Integer.toBinaryString(newrgbs [y*w+x]));
							 x++;
					                
							 		if (x==w){
					                	y++;
					                	x=0;
					                }
				              comp = rgbs[y*w+x];
						 	}
						 else {
							 numcomp ++;
						 }
					 }
					 else {
						 numbits ++;
					 }
					 
				 }
				 	 
		}
			 if (numcomp!=3){
				 newrgbs[y*w+x]=comp;
			 }

		BufferedImage dest = new BufferedImage(w,h,source.getType());
		dest.setRGB(0,0,w,h,newrgbs,0,w);
		return dest;
	}
	//-------------------------------Sens inversé Décodage-------------------------------//
	private static BufferedImage Décoder_Image_autresens(BufferedImage source) {
		  int w = source.getWidth();
		  int h = source.getHeight();
		  int[] rgbs = new int[w*h]; 
		  rgbs=source.getRGB(0,0,w,h,null,0,w);
		  int[] newrgbs=new int[rgbs.length];
		  int x;
		  int y;
		  int numcomp;
		  int nbits=nombre_bit.getValue();;
		  int brecup;
		  int masque=0;
		  int bit;
		  String texte="";
		  char carac=0;
		  int bit_carac=7;
		  char carac1=0;
		  char carac2=0;
		  char carac3=0;
		  char carac4=0;
		  String test="";
		  String textefinal="";
		  
		  
		  		for (y=0 ; y<h ; y++){ 
		  				for (x=0 ; x<w ; x++){
		  						for (numcomp=3 ; numcomp>=1 ; numcomp--){
		  								for (int numbit=8-nbits; numbit<8; numbit++){
		  									
									      masque=	1<< (8*numcomp-numbit-1);
									      brecup= rgbs[y*w+x] & masque;
									      if (brecup==0)
									       bit=0;
									      else
									       bit=1;
									      
									      
									      
									      
									      if(bit_carac>=0){
											      carac=(char) (carac+(char)(bit<<bit_carac));
											      bit_carac--;
											   
											     
									      }
									      if (bit_carac < 0) {
									    	
									    	  texte = texte + carac;
									    	
									    	  
									    	  carac4=carac3;
									    	  carac3=carac2;
									    	  carac2=carac1;
									    	  carac1=carac;
									    	  
									    	  bit_carac=7;
									    	  carac=0;

									    	  test=""+carac4 +carac3 + carac2 + carac1;  
									    	
									    	  
									    	  if (test.equals("!3wZ")){
									    		  numbit=9;
									    		  numcomp=0-1;
									    		  x=w;
									    		  y=h;
									    	  }
									      }
		    	}
		      }
		    }
		  } 
		 	int taille_text=texte.length();
				  
			  for (int i=0;i<taille_text-4;i++){
				  char caracfinal = texte.charAt(i);
				  textefinal = textefinal + caracfinal;
			  }	
		  
		  zone_de_texte.setText("");
		  zone_de_texte.insert(textefinal, 0);
		  
		  BufferedImage dest = new BufferedImage(w,h,source.getType());
		  dest.setRGB(0,0,w,h,newrgbs,0,w);
		  return dest;
		 }
	
	
	
	
	
	
	
	// ----------------------------------------- Bon sens Décodage ------------------------------------------//
	
	
	private static BufferedImage Décoder_Image_bonsens(BufferedImage source) {
		  int w = source.getWidth();
		  int h = source.getHeight();
		  int[] rgbs = new int[w*h]; 
		  rgbs=source.getRGB(0,0,w,h,null,0,w);
		  int[] newrgbs=new int[rgbs.length];
		  int x;
		  int y;
		  int numcomp;
		  int nbits=nombre_bit.getValue();
		  int brecup;
		  int masque=0;
		  int bit;
		  String texte="";
		  char carac=0;
		  int bit_carac=7;
		  char carac1=0;
		  char carac2=0;
		  char carac3=0;
		  char carac4=0;
		  String textefinal="";
		  String test="";
		  		
		  		for (y=0 ; y<h ; y++){ 
		  				for (x=0 ; x<w ; x++){
		  						for (numcomp=0 ; numcomp<3 ; numcomp++){
		  								for (int numbit=0; numbit<nbits; numbit++){
		  									
									      masque=	1 << (8*numcomp+numbit);
									      brecup= rgbs[y*w+x] & masque;
									      
				 					      if (brecup==0)
									       bit=0;
									      else
									       bit=1;
									      
									       
									      if(bit_carac>=0){
											      carac=(char) (carac+(char)(bit<<bit_carac));
											      bit_carac--;
									      }
									      if (bit_carac < 0) {
									    	 
										    	  texte = texte + carac;
										    	  
										    	  carac4=carac3;
										    	  carac3=carac2;
										    	  carac2=carac1;
										    	  carac1=carac;
										    	  

										    	  test=""+carac4 +carac3 + carac2 + carac1;  
										    	  System.out.println(test);
										    	  
										    	  bit_carac=7;
										    	  carac=0; 
										    	 
										    	  if (test.equals("!3wZ")){
										    		  numbit=nbits;
										    		  numcomp=4;
										    		  x=w;
										    		  y=h;
										    		
										    	  }
									      }
									      
		    	}
		      }
		    }
		  } 
		  
		  int taille_text=texte.length();
		  
		  for (int i=0;i<taille_text-4;i++){
			  char caracfinal = texte.charAt(i);
			  textefinal = textefinal + caracfinal;
		  }	
		  		
		  zone_de_texte.setText("");
		  zone_de_texte.insert(textefinal, 0);
		  
		  BufferedImage dest = new BufferedImage(w,h,source.getType());
		  dest.setRGB(0,0,w,h,newrgbs,0,w);
		  return dest;
	}}
	
	
	
	
	
	
	
	// ------------------------------------ Ludovic ------------------------------------//
		  		
		
		/*int w = source.getWidth();
		  int h = source.getHeight();
		  int[] rgbs = new int[w*h]; 
		  rgbs=source.getRGB(0,0,w,h,null,0,w);
		  int[] newrgbs=new int[rgbs.length];
		  int x;
		  int y;
		  int comp;
		  int bimage;
		  int n=4;
		  int compT;
		  int compR;
		  int compV;
		  int compB;
		  int brecup;
		  int[] code = {1,2,4,8};
		  int nbbitstotal=w*h*4*n;
		  int[] phrase= new int[nbbitstotal];
		  int i=0;
		  String phrasefinale="";
		  
		  char chaine_bit_finale=0;

		  
		  	for (y=0 ; y<h-1 ; y++){ 
		  		for (x=0 ; x<w-1 ; x++){
		  			for (comp=0 ; comp<=3 ; comp++){
		  				for (bimage=n; bimage>=2; bimage--){
		    			
						  	   if (comp==0){
						       compT= (rgbs[y*w+x] & 0xFF000000) >>24;
								       
								       if (bimage==4){
								        brecup=(compT & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								       if (bimage==3){ 
								        brecup=(compT & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								       if (bimage==2){ 
								        brecup=(compT & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								       if (bimage==1){
								        brecup=(compT & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								         }
		         
						  	   if (comp==1){
						  	   compR= (rgbs[y*w+x]& 0x00FF0000) >>16;
		         
								       if (bimage==4){
								        brecup=(compR & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								       if (bimage==3){ 
								        brecup=(compR & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								       if (bimage==2){ 
								        brecup=(compR & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								       if (bimage==1){
								        brecup=(compR & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								         }
		         
						  	   if (comp==2){
						  	   compV= (rgbs[y*w+x]& 0x0000FF00) >>8;
		        
										if (bimage==4){
								        brecup=(compV & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								       if (bimage==3){ 
								        brecup=(compV & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								       if (bimage==2){ 
								        brecup=(compV & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								       if (bimage==1){
								        brecup=(compV & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
							          
											  	   }
							         
						  	   if (comp==3){
		                       compB= (rgbs[y*w+x]& 0x000000FF) >>0;
		                       
								        if (bimage==4){
								        brecup=(compB & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								       if (bimage==3){ 
								        brecup=(compB & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								       if (bimage==2){ 
								        brecup=(compB & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								       if (bimage==1){
								        brecup=(compB & code[bimage-1])>>(bimage-1);
								        phrase[i]=brecup;
								        i++;
								       }
								          
							  	}
						  	   System.out.println(phrase[i-1]);
		     }
		     
		  		int carac=0;
		  		chaine_bit_finale=0;
		     for(int loop=0; loop<=i; loop++){
		     		char bit_decale=(char) (phrase[loop]<<(7-carac));
		    	 	chaine_bit_finale= (char) (chaine_bit_finale + bit_decale);
			        carac++;
			        if (carac==8) carac=0;
			      
	      			phrasefinale=phrasefinale+chaine_bit_finale;
	      			//System.out.println(phrasefinale);
		     	}
				   
				    
		  	}  			
		   }
		  } 
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  BufferedImage dest = new BufferedImage(w,h,source.getType());
		  dest.setRGB(0,0,w,h,newrgbs,0,w);
		  return dest;
		 }*/
	
	/*private static BufferedImage Traitement_simple(BufferedImage source) {
		
		
		int w = source.getWidth();
		int h = source.getHeight();
		int[] rgbs = new int[w*h]; 
		rgbs=source.getRGB(0,0,w,h,null,0,w);
		int[] newrgbs=new int[rgbs.length];
		int x,y;
		int compR;
		int compV;
		int compB;
		int compG;
		int xbis;
		int ybis;*/
		
		
		/*for(x=0;x<w;x=x+1)
		
		{

			for(y=0;y<h;y=y+1)
			{
				compR=(rgbs[y*w+x] & 0x00FF0000);
				compV=(rgbs[y*w+x] & 0x0000FF00);
				compB=(rgbs[y*w+x] & 0x000000FF);
				rgbs[y*w+x]=(compR>>16)+(compV>>8)+(compB>>0);
				compG=rgbs[y*w+x] / 3;      					                            // niveau de gris
				newrgbs[y*w+x]=((255<<24)|(compG<<16)|(compG<<8)|(compG<<0));
				
				/*if(compG<=128)                             // noir et blanc
					newrgbs[y*w+x]=0xFF000000;	
				else
					newrgbs[y*w+x]=0xFFFFFFFF;*/
				
				
				
				//newrgbs[(w*h)-(y+1)*w+x]=rgbs[y*w+x]; //-> reflexion horizontale
				
				/*xbis=w-x-1;    //réflexion V
				ybis=y;*/
				
				/*xbis=x;				// réflexion H
				ybis=h-1-y;*/  
				
				
				//newrgbs[ybis*w+xbis]=rgbs[y*w+x];
				
				
			    /*if(y<=h/2)        // -> effet miroir horizontal        
				xbis=x;                    
				ybis=h-y;              
				
				newrgbs[ybis*w+xbis]=rgbs[y*w+x];
				
					
				
					
			}
		}*/

		
		
		
		
		
		
		
		
		
		
		
		//----------------------------------Steganographie--------------------------------


        /* for (c=0;c<=14;c++) // On parcourt tous les caractères
         {
             
             int bit= new Byte(bytes[c]).intValue(); // On met la valeur receuilli par getBytes dans un int
             
             for(i=0;i<=7;i++) { // On extrait chaques bits et on le mets dans un tableau
                
                 if (i==0){
                bits[i] = (bit & 0x80)>>7;
                 }
                 else if (i==1){
                bits[i] = (bit & 0x40)>>6;
                 }
                 else if (i==2) {
                bits[i] = (bit & 0x20)>>5;    
                 }
                 else if (i==3){
                bits[i]= (bit & 0x10)>>4;
                 }
                 else if (i==4){
                bits[i] = (bit & 0x8)>>3;
                 }
                 else if (i==5){
                bits[i] = (bit & 0x4)>>2;     
                 }
                 else if (i==6){
                bits[i]= (bit & 0x2)>>1;     
                 }
                 else {
                bits[i] = (bit & 0x1)>>0;
                 }
                
                System.out.println("BIT CODE "+ bits[i]);
                
     
                 
                 
             int compoT = (rgbs[y*w+x]&(0xFF000000))>>24;
            // char essai=(char)compoT;
             int compoR = (rgbs[y*w+x]&(0x00FF0000))>>16;
             int compoV = (rgbs[y*w+x]&(0x0000FF00))>>8;
             int compoB = (rgbs[y*w+x]&(0x000000FF))>>0;
             
             System.out.println(rgbs[y*w+x]);
             System.out.println(compoR);
             System.out.println(essai);
             
             
             for (a=0;a<=7;a++){// On met sous forme de bits, les composantes dans des tableaux
                         if (a==0){
                             compT[a] = (compoT & 0x80)>>7;
                             compR[a] = (compoR & 0x80)>>7;
                             compV[a] = (compoV & 0x80)>>7;
                             compB[a] = (compoB & 0x80)>>7;
                         }
                         else if (a==1){
                             compT[a] = (compoT & 0x40)>>6;
                             compR[a] = (compoR & 0x40)>>6;
                             compV[a] = (compoV & 0x40)>>6;
                             compB[a] = (compoB & 0x40)>>6;
                         }
                         else if (a==2) {
                             compT[a] = (compoT & 0x20)>>5;    
                             compR[a] = (compoR & 0x20)>>5;    
                             compV[a] = (compoV & 0x20)>>5;    
                             compB[a] = (compoB & 0x20)>>5;    
                         }
                         else if (a==3){
                             compT[a]=  (compoT & 0x10)>>4;
                             compR[a]=  (compoR & 0x10)>>4;
                             compV[a]=  (compoV & 0x10)>>4;
                             compB[a]=  (compoB & 0x10)>>4;
                         }
                         else if (a==4){
                             compT[a] = (compoT & 0x8)>>3;
                             compR[a] = (compoR & 0x8)>>3;
                             compV[a] = (compoV & 0x8)>>3;
                             compB[a] = (compoB & 0x8)>>3;
                         }
                         else if (a==5){
                             compT[a] = (compoT & 0x4)>>2;    
                             compR[a] = (compoR & 0x4)>>2;
                             compV[a] = (compoV & 0x4)>>2;
                             compB[a] = (compoB & 0x4)>>2;
                         }
                         else if (a==6){
                             compT[a]= (compoT & 0x2)>>1;     
                             compR[a]= (compoR & 0x2)>>1;
                             compV[a]= (compoV & 0x2)>>1;
                             compB[a]= (compoB & 0x2)>>1;
                         }
                         else {
                             compT[a] = (compoT & 0x1)>>0;
                             compR[a] = (compoR & 0x1)>>0;
                             compV[a] = (compoV & 0x1)>>0;
                             compB[a] = (compoB & 0x1)>>0;
                         }
             	}
         
			            if (j<=nbits) {        // On change le bit d'une composante par celui de notre message
			            	compT[8-nbits+m]=bits[i];
			            	j++;
			            	m++;
			            	System.out.println(compT[i]);
			            	
				            	if (j==nbits)
				            	{
				            		m=0;
				            	}
			            
			            }
			            
			            else if (j>nbits && j <= (2*nbits)){
			            compR[8-nbits+m]=bits[i];    
			            j++;
			            m++;
			            
					            if (j==nbits*2)
					            {
					                m=0;
					            }
			            }
			            else if (j>nbits*2 && j <= (3*nbits)){
			            compV[8-nbits+m]=bits[i];
			            j++;
			            m++;
			            
					            if (j==nbits*3)
					            {
					                m=0;
					            }
			            }
			            else
			            {
			            compB[8-nbits+m]=bits[i];
			            j++;
			            m++;
			            
		
					            if (j==nbits*4)
					            {
					                m=0;
					                j=0;
					                
					                System.out.println(compT[0]);
				                    System.out.println(compT[1]);
				                    System.out.println(compT[2]);
				                    System.out.println(compT[3]);
					                
					                
					                
					                for (int k=0; k<7;k++){ // On remet sous la forme d'un int notre composante
					                    valeurT= valeurT & (compT[k]<<(7-k));
					                    valeurR= valeurR & (compR[k]<<(7-k));
					                    valeurV= valeurV & (compV[k]<<(7-k));
					                    valeurB= valeurB & (compB[k]<<(7-k));
					                    }
					                    
					                   System.out.println(valeurT);
					                    System.out.println(valeurR);
					                    System.out.println(valeurV);
					                    System.out.println(valeurB);
					                    
					                    newrgbs[y*w+x]= (valeurT<<24) |  (valeurR<<16) | (valeurV<<8) | (valeurB);
					                
					                    			                    valeurT= 0;
					                    valeurR= 0;
					                    valeurV= 0;
					                    valeurB= 0;
					                
					                    				x++;
					                
										                if (x==w){
										                	y++;
										                	x=0;
										                }
					            }	
			            }
           
             }
         } 
                
    */
         
         
         
         
         
                
                 // on réinsère dans une nouvelle image
             

                
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//------------------------------- pixelisation -----------------------------------
		/*int w = source.getWidth();
		int h = source.getHeight();
		int[] rgbs = new int[w*h]; 
		rgbs=source.getRGB(0,0,w,h,null,0,w);
		int[] newrgbs=new int[rgbs.length];
		int x,y;
		int xbis, ybis;
		int taille_matrice;                 // impair
		int w_m;
		int compR, compB,compV;
		int sommeR=0,sommeV=0,sommeB=0;
		int count;
		
		taille_matrice=mon_slider.getValue();
		taille_matrice=taille_matrice*2+1;
		w_m=(taille_matrice-1)/2;
		
		
		for (x=w_m;x<w;x=x+taille_matrice){
			
			for (y=w_m;y<h;y=y+taille_matrice){
				
				count=0;
				sommeR=0;
				sommeV=0;
				sommeB=0;
				for (xbis=x-w_m;xbis<=x+w_m;xbis++){
					
					for (ybis=y-w_m;ybis<=y+w_m;ybis++){
							
						if(x<=w && xbis<w && ybis<h && y<=h){
							
							compR=(rgbs[ybis*w+xbis]&(0x00FF0000))>>16;
							compV=(rgbs[ybis*w+xbis]&(0x0000FF00))>>8;
							compB=(rgbs[ybis*w+xbis]&(0x000000FF))>>0;
						
							sommeR=sommeR+compR;
							sommeV=sommeV+compV;
							sommeB=sommeB+compB;
							
							count++;
						}
						
						
					}
				}
				

					sommeR=sommeR/(count);
					sommeV=sommeV/(count);
					sommeB=sommeB/(count);
				
			
				for (xbis=x-w_m;xbis<=x+w_m;xbis++){
					
					for (ybis=y-w_m;ybis<=y+w_m;ybis++){
						
						if(x<=w && xbis<w && ybis<h && y<=h){
							newrgbs[ybis*w+xbis]=((0xFF000000)|(sommeR<<16)|(sommeV<<8)|(sommeB<<0));
						}
					}
				}		
		}	
	}*/
			

		/*BufferedImage dest = new BufferedImage(w,h,source.getType());
		dest.setRGB(0,0,w,h,newrgbs,0,w);
		return dest;*/
	//}

	/**
	 * @param source : l'image à traiter
	 * @return l'image traitée par le filtre
	 */
	//private static BufferedImage Traitement_par_filtage(BufferedImage source) {
		//int[] filtre = new int[3*3];

		// PARTIE A COMPLETER : définition du filtre
		
		/*filtre[0]=1;
		filtre[1]=1;
		filtre[2]=1;
		filtre[3]=1;
		filtre[4]=1;
		filtre[5]=1;
		filtre[6]=1;
		filtre[7]=1;
		filtre[8]=1;
		int diviseur=0;
		int l;*/
		
		
		// PARTIE A COMPLETER : calcul du diviseur
		
		
		/*for(l=0;l<=8;l++)
		{
			diviseur=diviseur+filtre[l];
				
		}*/
		
		
		// PARTIE A COMPLETER: 
		
		/*int w = source.getWidth();
		int h = source.getHeight();
		int[] rgbs = new int[w*h]; 
		rgbs=source.getRGB(0,0,w,h,null,0,w);
		int[] newrgbs=new int[rgbs.length];
		int count;
		int pixel,newpixel;
		int xb;
		int yb;
		int x;
		int y;
		int compG;
		int taille_matrice=3;
		int taille_matrice1;
		int somme=0;*/
		
		
	/*for(x=0;x<w;x=x+1){
			
		for(y=0;y<h;y=y+1){
			taille_matrice1=(taille_matrice-1)/2;
		
			count=0;
			for(xb=x-taille_matrice1;xb<=x+taille_matrice1;xb=xb+1){
					
				for(yb=y-taille_matrice1;yb<=y+taille_matrice1;yb=yb+1){
					
					if(xb>=0 && xb<w && yb>=0 &&  yb<h)
						pixel=(rgbs[yb*w+xb] & 0x000000FF*filtre[count]);
					
					else
						pixel=((rgbs[y*w+x] & 0x000000FF)*filtre[count]);
					
					count++;       //incrémentation
					
					somme=somme+pixel;
					
				}
			}
			newpixel=somme/taille_matrice; //sans utiliser au diviseur*/
		
			/*if(diviseur!=0) 
				newpixel=somme/diviseur; //en utilisant diviseur
			else 
				newpixel=somme+128;
				if(newpixel>255) newpixel=255;
				if(newpixel<0) newpixel=0;
			
			newrgbs[y*w+x]=((0xFF000000)|(newpixel<<16)|(newpixel<<8)|(newpixel<<0));

		}
	}
		BufferedImage dest = new BufferedImage(w,h,source.getType());
		dest.setRGB(0,0,w,h,newrgbs,0,w);
		return dest;
	}*/

	/**
	 * @return une image en dégradé de couleur
	 */
