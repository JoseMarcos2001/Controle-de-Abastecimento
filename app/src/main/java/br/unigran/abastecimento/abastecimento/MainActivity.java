package br.unigran.abastecimento.abastecimento;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.unigran.abastecimento.R;
import br.unigran.abastecimento.bancoDados.CadastroDB;
import br.unigran.abastecimento.bancoDados.DBHelper;

public class MainActivity extends AppCompatActivity {

    private EditText nmr1;
    private EditText nmr2;
    private TextView resultado;

    public double valor1, valor2;

    EditText quilometragem;
    EditText quantidade_abastecida;
    EditText data;
    EditText valor;
    ListView listagem;
    List<Cadastro> dados;
    DBHelper db;
    CadastroDB contatoDB;
    Integer atualiza;
    Integer confirma = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nmr1 = findViewById(R.id.quilometragemID);
        nmr2 = findViewById(R.id.quantidade_abastecidaID);
        resultado = findViewById(R.id.textView2);

        db = new DBHelper(this);
        quilometragem = findViewById(R.id.quilometragemID);
        quantidade_abastecida = findViewById(R.id.quantidade_abastecidaID);
        data = findViewById(R.id.diaID);
        valor = findViewById(R.id.valorID);
        listagem = findViewById(R.id.listID);
        dados = new ArrayList();
        ArrayAdapter adapter = new ArrayAdapter(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dados);
        listagem.setAdapter(adapter);
        contatoDB = new CadastroDB(db);
        contatoDB.lista(dados);
        acoes();
    }
    private void acoes() {
        confirma = null;
        listagem.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView,
                                                   View view, int i, long l) {
                        AlertDialog.Builder mensagem = new AlertDialog.Builder(view.getContext());
                        mensagem.setTitle("Opções");
                        mensagem.setMessage("Escolha a opção que deseja realizar");
                        mensagem.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                new AlertDialog.Builder(view.getContext())
                                        .setMessage("Deseja realmente remover")
                                        .setPositiveButton("Confirmar",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface,
                                                                        int k) {
                                                        contatoDB.remover(dados.get(i).getId());
                                                        contatoDB.lista(dados);
                                                        ((ArrayAdapter) listagem.getAdapter()
                                                        ).notifyDataSetChanged();
                                                        String msg1 = "Cadastro removido com sucesso";
                                                        Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                        .setNegativeButton("cancelar", null)
                                        .create().show();
                            }
                        });
                        mensagem.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                atualiza = dados.get(i).getId();
                                quilometragem.setText(dados.get(i).getQuilometragem());
                                quantidade_abastecida.setText(dados.get(i).getQuantidade_abastecida().toString());
                                data.setText(dados.get(i).getData().toString());
                                valor.setText(dados.get(i).getValor().toString());

                                contatoDB.atualizar(dados.get(i));
                                contatoDB.lista(dados);

                                confirma = 1;

                            }
                        });
                        mensagem.setNeutralButton("Cancelar", null);
                        mensagem.show();
                        return false;
                    }
                });
    }

    public boolean verificar() {
        String s1 = quilometragem.getText().toString();
        String s2 = quantidade_abastecida.getText().toString();
        String s3 = data.getText().toString();
        String s4 = valor.getText().toString();
        if ((s1.equals(null) || s2.equals(null) || s3.equals(null)|| s4.equals(null))
                || (s1.equals("") || s2.equals("") || s3.equals("") || s4.equals(""))) {
            Toast.makeText(this, "Preencha os campos", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            valor1 = Double.parseDouble(nmr1.getText().toString());
            valor2 = Double.parseDouble(nmr2.getText().toString());
            return true;

        }
    }
    public void salvar(View view) {
        if (verificar()) {
            Cadastro cadastro = new Cadastro();
            if (atualiza != null) {
                cadastro.setId(atualiza);

                contatoDB.lista(dados);
                Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();


            }
            cadastro.setQuilometragem(quilometragem.getText().toString());
            cadastro.setQuantidade_abastecida(quantidade_abastecida.getText().toString());
            cadastro.setData(data.getText().toString());
            cadastro.setValor(valor.getText().toString());

            if (atualiza != null)
                contatoDB.atualizar(cadastro);
            else {
                contatoDB.inserir(cadastro);
                contatoDB.lista(dados);
                Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                resultado.setText(String.valueOf(valor1/valor2));
            }
            contatoDB.lista(dados);
            listagem.invalidateViews();
            limpar();
            atualiza = null;
            confirma = null;
        }
    }
    private void limpar() {
        quilometragem.setText("");
        quantidade_abastecida.setText("");
        data.setText("");
        valor.setText("");
    }

    @Override
    public void onBackPressed() {
        if (confirma != null) {
            limpar();
            String msgCancelar = "Edição cancelada";
            Toast.makeText(getApplicationContext(), msgCancelar, Toast.LENGTH_SHORT).show();
            confirma = null;
        } else {
            super.onBackPressed();
            limpar();
            String msgSair = "Saindo...";
            Toast.makeText(getApplicationContext(), msgSair, Toast.LENGTH_SHORT).show();

        }
    }

}

