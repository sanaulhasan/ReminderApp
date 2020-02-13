
package remainder;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import windows.ApplicationIndex;
import windows.OtherAlerts;
import windows.QuitApplication;

public class TrayHandler implements Runnable {
     TrayIcon tray;
     Image icon;
     MyConnection con = new MyConnection();
     @Override
    public void run() {
    
    showTray();
    
    
    }
    
    public void showTray(){
    try {
          
          icon = ImageIO.read((getClass().getResource("tray_icon.png")));
                } catch (IOException ex) {}
     
      tray = new TrayIcon(icon);  
      final PopupMenu menu = new PopupMenu();
      MenuItem add = new MenuItem("Add Alerts");
      MenuItem open = new MenuItem("Open Window");
      MenuItem exit = new MenuItem("Exit");
      menu.add(add);
      menu.addSeparator();
      menu.add(open);
      menu.addSeparator();
      menu.add(exit);
      //QuitApplication
      exit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                QuitApplication quit = new QuitApplication();
                quit.setVisible(true);
              
            }
            });
      add.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                OtherAlerts alert = new OtherAlerts();
                alert.setVisible(true);
              
            }
            });
      
      open.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ApplicationIndex x = new ApplicationIndex();
         x.setVisible(true);
              
            }
            });
      
      
      
      
      
      tray.setPopupMenu(menu);
      final SystemTray systemtray = SystemTray.getSystemTray();
      try {
          systemtray.add(tray);
                } catch (AWTException ex) {}
       
      
        
    
    }
     
    
    
    
}
