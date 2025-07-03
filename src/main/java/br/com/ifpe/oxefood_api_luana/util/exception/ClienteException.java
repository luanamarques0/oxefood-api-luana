package br.com.ifpe.oxefood_api_luana.util.exception;

public class ClienteException extends RuntimeException {

    public static final String MSG_TELEFON_INVALIDO = "Telefone inválido: o número deve começar com o DDD 81.";

    public ClienteException(String msg) {
        super(String.format(msg));
    }
;}
