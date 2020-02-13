
package remainder;

import windows.ApplicationIndex;


public class Remainder {

    public static void main(String[] args) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {}
        
        
        
       
        TrayHandler tray = new TrayHandler();
        new Thread(tray).start();
        
        AutoUpdateDates update = new AutoUpdateDates();
        new Thread(update).start();
        
        ShowAlerts alert = new ShowAlerts();
        new Thread(alert).start();
        
        
         ApplicationIndex x = new ApplicationIndex();
         x.setVisible(true);
       
        
        
        
        
    }
    
}
