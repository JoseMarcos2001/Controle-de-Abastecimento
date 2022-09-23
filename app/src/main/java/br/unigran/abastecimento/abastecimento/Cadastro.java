package br.unigran.abastecimento.abastecimento;

public class Cadastro {
    private String quilometragem;
    private String quantidade_abastecida;
    private String data;
    private String valor;
    private Integer id;

    public String getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(String quilometragem) {
        this.quilometragem = quilometragem;
    }

    public String getQuantidade_abastecida() {
        return quantidade_abastecida;
    }

    public void setQuantidade_abastecida(String quantidade_abastecida) {
        this.quantidade_abastecida = quantidade_abastecida;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return
                "KM - " + quilometragem +
                        " | L - " + quantidade_abastecida +
                        " | Data - " + data +
                        " | R$ - " + valor ;
    }
}
