package principal;

import entidades.Usuario;
import interfaz.MenuGUI;
import servicios.SistemaInventario;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        boolean continuar = true;

        while (continuar) {

            SistemaInventario sistema = new SistemaInventario();

            String user = JOptionPane.showInputDialog("""
                    Usuario

                    Usuarios disponibles:

                    Encargado
                    usuario: jperez
                    contraseña: 1234

                    Empleado
                    usuario: mgomez
                    contraseña: 1234

        Ingrese el usuario:
        """);
            if (user == null) break;

            String pass = JOptionPane.showInputDialog("Contraseña:");
            if (pass == null) break;

            Usuario usuario = sistema.iniciarSesion(user, pass);

            if (usuario == null) {
                JOptionPane.showMessageDialog(null, "Credenciales inválidas");
                continue;
            }

            JOptionPane.showMessageDialog(null,
                    "Bienvenido " + usuario.getNombre());

            MenuGUI menu = new MenuGUI(sistema, usuario);
            menu.mostrarMenu();

            if (!menu.isCerrarSesion()) {
                continuar = false;
            }
        }
    }
}