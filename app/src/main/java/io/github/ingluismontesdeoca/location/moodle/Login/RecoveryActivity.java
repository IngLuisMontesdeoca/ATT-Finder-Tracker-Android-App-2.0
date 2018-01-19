package io.github.ingluismontesdeoca.location.moodle.Login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.github.ingluismontesdeoca.location.R;
import io.github.ingluismontesdeoca.location.include.validate;

public class RecoveryActivity extends AppCompatActivity {

    private Button btnRecovery;
    private TextView txtEmail;
    private RecoveryPaswordTask recoveryPasswordTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        this.initUI();
    }

    private void initUI(){
        showToolbar();
        this.btnRecovery = (Button) findViewById(R.id.btn_recovery_recovery);
        this.txtEmail = (TextView) findViewById(R.id.recovery_txt_login);
        this.btnRecovery.setOnClickListener(actionRecovery);
    }

    /*Habilitar toolBar*/
    private void showToolbar() {
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar_recovery);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private View.OnClickListener actionRecovery = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendProcess();
        }
    };

    private void sendProcess(){
        try{
            if( recoveryPasswordTask != null)
                return;

            String email = txtEmail.getText().toString();
            Boolean cancel = false;
            View focusView = null;

            if (TextUtils.isEmpty(email)) {
                txtEmail.setError(getString(R.string.error_field_required));
                focusView = txtEmail;
                cancel = true;
            } else if (!validate.isEmailValid(email)) {
                txtEmail.setError(getString(R.string.error_invalid_email));
                focusView = txtEmail;
                cancel = true;
            }

            if (cancel) {
                focusView.requestFocus();
            } else {
                showProgress(true);
                recoveryPasswordTask = new RecoveryPaswordTask();
                recoveryPasswordTask.execute((Void) null);
            }

        }catch(Exception e){

        }
    }

    private void showProgress(Boolean st){
        if( st ){
            findViewById(R.id.layout_progress).setVisibility(View.VISIBLE);
            findViewById(R.id.layout_recovery).setVisibility(View.GONE);
        }else{
            findViewById(R.id.layout_progress).setVisibility(View.GONE);
            findViewById(R.id.layout_recovery).setVisibility(View.VISIBLE);
        }
    }

    private class RecoveryPaswordTask extends AsyncTask<Void, Void, Boolean>{


        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                //Simular acceso a red
                Thread.sleep(2000);
                if(!searchEmailInDB())
                    return false;
            }catch (InterruptedException e){
                return false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            showProgress(false);
            if(!aBoolean)
                txtEmail.setError(getString(R.string.error_user_doesnot_exists));

        }
    }

    private Boolean searchEmailInDB(){
        Boolean ret = false;
        try{
            String email = txtEmail.getText().toString();
            //Buscar email en BD

        }catch(Exception e){
            txtEmail.setError(getString(R.string.error_sending_email));
        }
        return ret;
    }

    private void sendEmail(){
        try{
            //Proceso para generar key de recuperacion y enviarla por correo

        }catch(Exception e){
            txtEmail.setError(getString(R.string.error_sending_email));
        }
    }
}

