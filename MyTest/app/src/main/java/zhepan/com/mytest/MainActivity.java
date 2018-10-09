package zhepan.com.mytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button runBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runBtn= findViewById(R.id.runBtn);
    }

    /**
     * 点击按钮对应的方法
     * @param v
     */
    public void runMyUiautomator(View v){
        Log.i(TAG, "runMyUiautomator: ");
        new UiautomatorThread().start();
        Toast.makeText(this, "start run", Toast.LENGTH_SHORT).show();
    }

    /**
     * 运行uiautomator是个费时的操作，不应该放在主线程，因此另起一个线程运行
     */
    class UiautomatorThread extends Thread {
        @Override
        public void run() {
            super.run();
            String command = generateCommand("zhepan.com.mytestcase", "ExampleInstrumentedTest", "useAppContext");
            CMDUtils.CMD_Result rs= CMDUtils.runCMD(command,true,true);
            Log.e(TAG, "run: " + rs.error + "-------" + rs.success);
        }

        /**
         * 生成命令
         * @param pkgName uiautomator包名
         * @param clsName uiautomator类名
         * @param mtdName uiautomator方法名
         * @return
         */
        public  String generateCommand(String pkgName, String clsName, String mtdName) {
            String command = "am instrument -w -r -e debug false -e class "
                    + pkgName + "." + clsName + "#" + mtdName + " "
                    + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
            Log.e("test1: ", command);
            return command;
        }
    }
}
