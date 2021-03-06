
package tw.supra.any.manager;

import android.widget.ProgressBar;

import com.google.analytics.tracking.android.EasyTracker;

import tw.supra.any.R;
import tw.supra.any.app.MainActivity;
import tw.supra.any.views.PullToRefreshWebView;

public class UIManager {
    
    private static UIManager instance;
    private MainActivity mActivity;
    private ProgressBar mProgress;

    public static UIManager getInstance() {
        return instance;
    }

    private UIManager(MainActivity activity) {
        mActivity = activity;
    }

    public static void create(MainActivity activity) {
        if (instance == null) {
            instance = new UIManager(activity);
        }
    }
    
    public EasyTracker getGaTracker(){
        return EasyTracker.getInstance(mActivity.getApplicationContext());
    }

    private ProgressBar getProgressBar() {
        if (mProgress == null) {
            mProgress = (ProgressBar) mActivity.findViewById(R.id.progress);
        }
        return mProgress;
    }

    private void setProgress(int progress){
        getProgressBar().setProgress(progress);
    }
    
    public void onLoad(int newProgress){
        PullToRefreshWebView container = mActivity.getContainer();
        if(newProgress < 100){
            setProgress(newProgress);
            if(!container.isRefreshing()){
                container.setRefreshing(true);
            }
        }else {
            setProgress(0);
            onLoadComplete();
        }
    }
    
    public void onLoadComplete(){
        PullToRefreshWebView container = mActivity.getContainer();
        container.onRefreshComplete();
    }
}
