package com.chriscoliveira.contas2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class DespesaActivity extends ActionBarActivity {
    Toolbar mToolbar;

    int pos = -1;

    Banco banco = new Banco();
    ZUtilitarios zutil = new ZUtilitarios();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.despesa_activity);

		/*
         * codigo pagina
		 */
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        int mes = banco.RetornaMes();
        int ano = banco.RetornaAno();

        banco.carregaDados(this, "Pagar", "nao", "", "");
        banco.SomaExibe(this, mes + "", ano + "");

        final TextView tvmes;
        final TextView tvano;
        tvmes = (TextView) findViewById(R.id.tvMesFiltro);
        tvano = (TextView) findViewById(R.id.tvAnoFiltro);
        tvmes.setText(mes + "");
        tvano.setText(ano + "");

        ImageButton btVoltaMes, btAvancaMes;
        btVoltaMes = (ImageButton) findViewById(R.id.btVoltames);
        btAvancaMes = (ImageButton) findViewById(R.id.btAvancaMes);
        btVoltaMes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {

                    if (Integer.parseInt(tvmes.getText().toString()) <= 1) {
                        tvmes.setText("12");
                        tvano.setText("" + (Integer.parseInt(tvano.getText().toString()) - 1));
                        banco.SomaExibe(DespesaActivity.this, tvmes.getText().toString(), tvano.getText().toString());
                        banco.carregaDados(DespesaActivity.this, "Pagar", "nao", tvmes.getText().toString(), tvano.getText().toString());
                    } else {
                        tvmes.setText("" + (Integer.parseInt(tvmes.getText().toString()) - 1));
                        banco.SomaExibe(DespesaActivity.this, tvmes.getText().toString(), tvano.getText().toString());
                        banco.carregaDados(DespesaActivity.this, "Pagar", "nao", tvmes.getText().toString(), tvano.getText().toString());
                    }
                } catch (Exception e) {
                    zutil.toast(DespesaActivity.this, "Erro " + e);
                }
            }
        });

        btAvancaMes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {

                    if (Integer.parseInt(tvmes.getText().toString()) >= 12) {
                        tvmes.setText("1");
                        tvano.setText("" + (Integer.parseInt(tvano.getText().toString()) + 1));
                        banco.SomaExibe(DespesaActivity.this, tvmes.getText().toString(), tvano.getText().toString());
                        banco.carregaDados(DespesaActivity.this, "Pagar", "nao", tvmes.getText().toString(), tvano.getText().toString());
                    } else {
                        tvmes.setText("" + (Integer.parseInt(tvmes.getText().toString()) + 1));
                        banco.SomaExibe(DespesaActivity.this, tvmes.getText().toString(), tvano.getText().toString());
                        banco.carregaDados(DespesaActivity.this, "Pagar", "nao", tvmes.getText().toString(), tvano.getText().toString());
                    }
                } catch (Exception e) {
                    zutil.toast(DespesaActivity.this, "Erro " + e);
                }
            }
        });


        ListView lv = (ListView) findViewById(R.id.lvListagem);
        lv.setOnItemLongClickListener(new OnItemLongClickListener() {
            @SuppressWarnings("static-access")
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView TvId = (TextView) view.findViewById(R.id.tvId);
                pos = Integer.parseInt((String) TvId.getText());
                banco.dialog(DespesaActivity.this, "atz", "Pagar", "Atualizar Despesa", pos,
                        "nao");
                // createAndShowAlertDialog();

                return false;
            }
        });

		/*
         * fim do codigo
		 */

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("Despesas");
        mToolbar.setLogo(R.drawable.ico);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;

            case R.id.action_rendas:
                startActivity(new Intent(this, RendaActivity.class));
                finish();
                break;
            case R.id.action_cartao:
                startActivity(new Intent(this, CartaoActivity.class));
                finish();
                break;
            case R.id.action_export_email:
                banco.CriaListaParaExporacao(this);
                break;
            case R.id.action_novo:
                startActivity(new Intent(this, CadastroActivity.class));
                finish();
                break;
            case R.id.action_resumo:
                startActivity(new Intent(this, MesAMesActivity.class));
                finish();
                break;
            case R.id.action_settings:
                startActivity(new Intent(this, AjustesActivity.class));
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }

}
