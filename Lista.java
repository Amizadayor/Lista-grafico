import javax.swing.*;  //Importamos la libreria javax.swing que nos permite crear interfaces Graficas.
import java.awt.*;  //java.awt: proporciona una serie de clases e interfaces para realizar programas que se ejecuten en un entorno gráfico (AWT viene del inglés AbstractWindow Toolkit).//
import javax.swing.JOptionPane;  // javax.swing.JOptionPane: selecciona una serie de clases y metodo para la facilitacion de programas en el entorno gráfico
import java.awt.event.*;  //java.awt.event: Nos permite interactuar con los botones de la pantalla grafica.
import java.util.concurrent.*;  //java.util.concurrent: crea un pool de hilos que llevarán a cabo las tareas.
import java.util.logging.*;  //java.util.logging: Nos proporciona varios métodos con los que se pueden anotar cronológicamente los datos.

class Lista extends JFrame{  //JFrame crea una ventana gráfica
    private final int FILAS = 4;
    private static int MAX=0;
    private static int [] numero;
    private static int indice=-1;
    private JTextField txtDato;
    private JLabel lblTexto;  // JLabel: son textos que podemos colocar en un Frame.//
    private static JLabel[] lblNum;
    private static JLabel[] lblEtiqueta;
    private static JLabel[] lblDialogo;
    private static JLabel[] lblCompara;
    private int ancho=0, segmento=0, aa=72, cpm=45, ind;  //aa= ancho y alto
    private Graphics pantalla1;
    private Image pantalla2;

    public Lista(){   //ERROR, SE ROMPE EL PROGRAMA SI NO SE AGREGA NUMEROS ENTEROS, INCLUSO DESPUES DEL MENSAJE DE ERROR DE AGREGAR SOLO NUMEROS ENTEROS.//
      int v; // v: validacion
        do{
            try{
              v=0;
              MAX= Integer.parseInt (JOptionPane.showInputDialog(" INGRESE LA CANTIDAD DE ELEMENTOS A COLOCAR "));
              numero= new int[MAX];
              configuraVentana();
              inicializarComponentes();
            } catch(Exception e){
              JOptionPane.showMessageDialog(null," INGRESE SOLO NUMEROS ENTEROS."," ERROR ",JOptionPane.ERROR_MESSAGE);
              v=1;
              return;
            }
        }while(v != 0);
    }
    private void inicializarComponentes(){
      lblNum = new JLabel[MAX];
      lblEtiqueta = new JLabel[MAX];
      lblDialogo = new JLabel[MAX];
      lblCompara = new JLabel[MAX];
      ancho=this.getWidth()-aa;
      segmento= ancho/(MAX+1);
      int posicion = 0;
        for(int i=0; i<MAX; i++){
          lblNum[i]= new JLabel();
          lblNum[i].setText("");
          posicion = segmento*(i+1);
          lblNum[i].setBounds (posicion,90,segmento,aa);
          lblNum[i].setHorizontalAlignment (SwingConstants.CENTER);
          lblNum[i].setBorder (BorderFactory.createLineBorder(new Color(0,0,0)));
          lblEtiqueta[i]= new JLabel();
          lblEtiqueta[i].setText("["+Integer.toString(i)+"]");
          lblEtiqueta[i].setBounds(posicion,70,segmento,10);
          lblEtiqueta[i].setHorizontalAlignment(SwingConstants.CENTER);
          lblDialogo[i]= new JLabel (" el == L["+i+"]");
          lblDialogo[i].setForeground(Color.GREEN);
          lblDialogo[i].setBounds(posicion,170,segmento,10);
          lblDialogo[i].setHorizontalAlignment(SwingConstants.CENTER);
          lblCompara[i]= new JLabel (" 0 == 9 ");
          lblCompara[i].setForeground(Color.BLACK);
          lblCompara[i].setBounds(posicion,186,segmento,10);
          lblCompara[i].setHorizontalAlignment(SwingConstants.CENTER);
          this.add (lblEtiqueta[i]);  //add: Es un método que nos permite añadir elementos a un Vector.//
          this.add (lblNum[i]);
          this.add (lblDialogo[i]);
          this.add (lblCompara[i]);
          lblDialogo[i].setVisible(false);  //setVisible: Se encarga de darle visibilidad de una clase. Si desde un objeto B quieres hacer visible o invisible otro objeto A, y A ha creado a B, B debe tener una referencia a A para llamar al método .setVisible(true).//
          lblCompara[i].setVisible(false);  //setVisible: Se encarga de darle visibilidad de una clase. Si desde un objeto B quieres hacer visible o invisible otro objeto A, y A ha creado a B, B debe tener una referencia a A para llamar al método .setVisible(true).//
        }
      JPanel panel = new JPanel ();
      panel.setBounds (40,225, this.getWidth()-77,45);  // aquí definimos la posicion y tamaño.
      panel.setBorder (BorderFactory.createLineBorder(new Color(0,0,0)));  //aquí cambiamos los bordes a color negro.
      this.add(panel); // Se agrega.

      JButton btnEditar = new JButton (" EDITAR ");  // Creamos los botones para las funciones .
        btnEditar.addActionListener (new ActionListener(){
            public void actionPerformed (ActionEvent evt){
              btnEditarActionPerformed (evt);
            }
        });
      panel.add(btnEditar);
      JButton btnBorrar = new JButton (" BORRAR ");
        btnBorrar.addActionListener (new ActionListener(){
            public void actionPerformed (ActionEvent evt){
              btnBorrarActionPerformed (evt);
            }
        });
      panel.add(btnBorrar);
      JButton btnBuscar = new JButton (" BUSCAR ");
        btnBuscar.addActionListener (new ActionListener(){
            public void actionPerformed (ActionEvent evt){
              btnBuscarActionPerformed (evt);
            }
        });
      panel.add(btnBuscar);  // Agregamos los botones a la ventana grfica.
      lblTexto = new JLabel (" INGRESE DATOS ");
      lblTexto.setBounds (300,15,100,30);
      txtDato = new JTextField();
      txtDato.setBounds(400,17,50,25);
        txtDato.addActionListener (new ActionListener(){
            public void actionPerformed (ActionEvent evt){
              txtDatoActionPerformed (evt);
            }
        });
      JLabel lblNombre = new JLabel ("LISTA");
      lblNombre.setBounds(15,103,40,40);
      this.add(lblNombre);
      this.add(lblTexto);
      this.add(txtDato);
    }
    public void configuraVentana(){
      this.setTitle(" LISTAS ESTATICAS ");
      this.setSize (500,320);
      this.setLayout(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //setDefaultCloseOperation: se utiliza para especificar una de varias opciones para el botón de cierre. JFrame.EXIT_ON_CLOSE: Terminar los subprocesos.//
      this.setLocationRelativeTo(null);  //setLocationRelativeTo: coloca la ventana en una posición relativa a un componente que le pasemos como parámetro.//
      this.setResizable(false);  //setResizable: Esta funcion evita que el usuario cambie o no el tamaño del marco.//
    }
    private void btnEditarActionPerformed (ActionEvent evt){
      int ps,nv;  //ps:posicion a cambiar  v:nuevo valor
      int v;  // validacion.
        do{
            try{
              v=0;
              ps= Integer.parseInt(JOptionPane.showInputDialog(this," INGRESE LA POSICION DE ARREGLO A MODIFICAR :"));
              nv= Integer.parseInt(JOptionPane.showInputDialog(this," INGRESE EL NUEVO VALOR"));
              numero[ps]=nv;
              lblNum[ps].setText(Integer.toString(nv));
            }catch (Exception e){
              JOptionPane.showMessageDialog(null," INGRESE SOLO NUMEROS ENTEROS."," ERROR ",JOptionPane.ERROR_MESSAGE);
              v=1;
              return;
            }
        }while(v !=0);
    }
    private void btnBorrarActionPerformed (ActionEvent evt){
      int ps;
      int v;
        do{
            try{
              v=0;
              ps= Integer.parseInt(JOptionPane.showInputDialog(this," INGRESE LA POSICION DE ARREGLO A ELIMINAR : "));
                for (int i=ps;i<indice;i++){
                  numero[i]=numero[i+1];
                  lblNum[i].setText(lblNum[i+1].getText());
                }
              lblNum[indice].setText("");
              indice--;
              txtDato.setEnabled(true);
            }catch(Exception e){
              JOptionPane.showMessageDialog(null," INGRESE SOLO NUMEROS ENTEROS."," ERROR ",JOptionPane.ERROR_MESSAGE);
              v=1;
              return;
            }
        }while(v !=0);
    }
    private void btnBuscarActionPerformed (ActionEvent evt){
      int ps;
      boolean encontrado= false;
      int v;  // v: validacion
        do{
            try{
              v=0;
              ps= Integer.parseInt(JOptionPane.showInputDialog(this," INGRESE LA POSICION DE ARREGLO A BUSCAR "));
                for (int i=0;i<=indice;i++){
                  lblDialogo[i].setVisible(true);
                  lblCompara[i].setText(ps + "==" + numero [i]);
                  lblCompara[i].setVisible(true);
                    try{
                      this.update(this.getGraphics());
                      TimeUnit.MILLISECONDS.sleep(1000);
                    }catch (InterruptedException ex){
                      JOptionPane.showMessageDialog(null," A OCURRIDO UN PROBLEMA, REVISE SU CODIDO. ","ERROR ",JOptionPane.ERROR_MESSAGE);
                      ex.printStackTrace();
                      System.exit(0);
                    }
                  lblDialogo[i].setVisible(false);
                  lblCompara[i].setVisible(false);
                    if (ps == numero[i]){
                      JOptionPane.showMessageDialog(this," ELEMENTO ENCONTRADO EN LA POSICION NUMERO : " + ps);
                      encontrado = true;
                      return;
                    }
                }
            }catch(Exception e){
              JOptionPane.showMessageDialog(null," INGRESE SOLO NUMEROS ENTEROS."," ERROR ",JOptionPane.ERROR_MESSAGE);
              v=1;
            }
        }while (v!=0);
          if (!encontrado)
          JOptionPane.showMessageDialog(null," NO SE ENCONTRO EL ELEMENTO "," ERROR ",JOptionPane.ERROR_MESSAGE);
    }

    private void txtDatoActionPerformed (ActionEvent evt){
      int v;  // v: validacion
        do{
            try{
              v=0;
              indice++;
              numero [indice]= Integer.parseInt(txtDato.getText());
              lblNum[indice].setText(txtDato.getText());
              txtDato.setText("");
              txtDato.requestFocus();
              if (indice==(MAX-1))
              txtDato.setEnabled(false);
            }catch(Exception e){
              JOptionPane.showMessageDialog(null," INGRESE SOLO NUMEROS ENTEROS."," ERROR ",JOptionPane.ERROR_MESSAGE);
              v=1;
              indice--;
              return;
            }
        }while (v!=0);
    }
    @Override  // @Override  : Nos vermite sobreescribir o modificar la declaración de un método en una clase superior.
    public void update(Graphics g){   // permite dibujar elipses, cuadrados, líneas, mostrar texto y mucho más.
      pantalla2=createImage (getWidth(),getHeight());
      pantalla1= pantalla2.getGraphics();
      paint(pantalla1);
      g.drawImage(pantalla2,0,0,this);
      return;
    }
    public static void main (String [] args){
      Lista l= new Lista();
      l.setVisible(true);   //setVisible: Se encarga de darle visibilidad de una clase. Si desde un objeto B quieres hacer visible o invisible otro objeto A, y A ha creado a B, B debe tener una referencia a A para llamar al método .setVisible(true).//
    }
}
