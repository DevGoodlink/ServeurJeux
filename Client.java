import java.net.*;
import java.util.*;
import java.io.*;
import java.util.regex.Pattern;


class Client{
    public static void main(String... args) {
        System.err.println("Lancement du client");
        Socket s ;
        ObjectInputStream fromServer;
        ObjectOutputStream toServer;
        
        try {
            s=new Socket(InetAddress.getLocalHost(), 60000);
            fromServer=new ObjectInputStream(s.getInputStream());
            toServer = new ObjectOutputStream(s.getOutputStream());
            toServer.writeObject(new Requete(null, "Hi I am a client could I play?",null,0L));
            Requete rep = (Requete)fromServer.readObject();
            System.out.println(rep.answer);
            if(rep.intent.contains("first")){
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                Requete req = new Requete();
                System.out.println("0-inscription, 1-Authentification");
                int a = Integer.parseInt(br.readLine());
                if(a==0){
                   // do{
                        Joueur j = signUp();
                        req=new Requete(j, "signup", "", 0L);
                        //System.out.println("envoi du joueur j="+j);
                   // }while(!rep.answer.equalsIgnoreCase("signup-success"));
                    
                }else if(a==1){
                    //do{
                        Joueur j = login();
                        req=new Requete(j, "login", "", 0L);

                        // System.out.println("envoi du joueur j="+j);

                   // }while(!rep.answer.equalsIgnoreCase(""));
                   
                }
                toServer.writeObject(req);
                Thread.sleep(5000);
                Thread.currentThread().join();
                rep = (Requete)fromServer.readObject();
                if(rep.answer.equalsIgnoreCase("login-success")){
                    System.out.println(rep.getJoueur());
                }




                
                /*if(rep.intent.compareTo("signup-success")==0){
                    System.err.println("Good! licence = "+rep.getJoueur().licence);
                }*/

            //toServer.writeObject(req);
            }
            
           /* while(( rep = (Requete)fromServer.readObject()) != null){
                System.out.println("[Server] says : -----------------------\n"+rep.answer);
               
            }*/
            s.close();
           // sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static Joueur signUp() {
        
        Scanner sc = new Scanner(System.in);
        Joueur j = new Joueur();
        System.out.println("Votre nom : ");
        j.nom= sc.nextLine();
        System.out.println("Votre prenom : ");
        j.prenom = sc.nextLine();
        sc.close();
        return j;

    }
    public static Joueur login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Votre nom : ");
        String nom= sc.nextLine();
        System.out.println("Votre prenom : ");
        String prenom = sc.nextLine();
        System.out.println("Votre licence : ");
        String l = sc.nextLine();
        int licence = Integer.parseInt(l);
        Joueur j = new Joueur(nom,prenom,licence);
        sc.close();
        return j;
    }
}