package br.unigran.abastecimento.bancoDados;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.unigran.abastecimento.abastecimento.Cadastro;

public class CadastroDB {
    private DBHelper db;
    private SQLiteDatabase conexao;

    public CadastroDB(DBHelper db) {
        this.db = db;
    }

    public void inserir(Cadastro cadastro) {
        conexao = db.getWritableDatabase();//abre o bd
        ContentValues valores = new ContentValues();
        valores.put("quilometragem", cadastro.getQuilometragem());
        valores.put("qtd_abastecida", cadastro.getQuantidade_abastecida());
        valores.put("data", cadastro.getData());
        valores.put("Valor", cadastro.getValor());
        conexao.insertOrThrow("Lista", null, valores);
        conexao.close();
    }

    public void atualizar(Cadastro cadastro) {
        conexao = db.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("quilometragem", cadastro.getQuilometragem());
        valores.put("qtd_abastecida", cadastro.getQuantidade_abastecida());
        valores.put("data", cadastro.getData());
        valores.put("Valor", cadastro.getValor());
        conexao.update("Lista", valores, "id=?", new String[]{cadastro.getId().toString()});
        conexao.close();
    }

    public void remover(int id) {
        conexao = db.getWritableDatabase();
        conexao.delete("Lista", "id=?",
                new String[]{id + ""});
    }

    public void lista(List dados) {
        dados.clear();
        conexao = db.getReadableDatabase();
        String names[] = {"id", "quilometragem", "qtd_abastecida", "data","Valor"};
        Cursor query = conexao.query("Lista", names,
                null, null, null,
                null, "quilometragem");
        while (query.moveToNext()) {
            Cadastro cadastro = new Cadastro();
            cadastro.setId(Integer.parseInt(
                    query.getString(0)));
            cadastro.setQuilometragem(
                    query.getString(1));
            cadastro.setQuantidade_abastecida(
                    query.getString(2));
            cadastro.setData(
                    query.getString(3));
            cadastro.setValor(
                    query.getString(4));
            dados.add(cadastro);
        }
        conexao.close();
    }
}
