package com.redsquirrel.neogrifter.notifikation.service;

        import android.content.ComponentName;
        import android.content.Context;
        import android.content.Intent;
        import android.content.ServiceConnection;
        import android.os.IBinder;

        import com.redsquirrel.neogrifter.notifikation.model.Notification;

/**
 * Intermédiaire entre application et le service.
 */
public class PlanifClient {

    /**
     * Service à piloter
     */
    private PlanifService planifService;

    /**
     *
     */
    private Context context;

    /**
     * Signale si le client est connecté au service
     */
    private boolean isConnected;

    public PlanifClient(Context pContext) {
        context = pContext;
    }


    public void doBindService() {
        context.bindService(new Intent(context, PlanifService.class), mConnection, Context.BIND_AUTO_CREATE);
        isConnected = true;
    }

    /**
     * When you attempt to connect to the service, this connection will be called with the result.
     * If we have successfully connected we instantiate our service object so that we can call methods on it.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with our service has been established,
            // giving us the service object we can use to interact with our service.
            planifService = ((PlanifService.ServiceBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            planifService = null;
        }
    };

    public void setNotification(Notification pNotif){
        if (planifService != null && isConnected) {
            planifService.setNotification(pNotif);
        } else {
            // TODO exception de quel type ?
        }
    }


    public void doUnbindService() {
        if (isConnected) {
            context.unbindService(mConnection);
            isConnected = false;
        }
    }
}