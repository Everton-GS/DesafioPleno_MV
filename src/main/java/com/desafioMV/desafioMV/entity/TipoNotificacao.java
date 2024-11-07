package com.desafioMV.desafioMV.entity;

public enum TipoNotificacao {

       INFORMATIVO("Informativo"),
       PROMOCAO("Promoção"),
       ALERTA("Alerta");

       private String tipoNotificacao;

       private TipoNotificacao(String tipoNotificacao){
              this.tipoNotificacao=tipoNotificacao;
       }
       public String getTipoNotificacao(){
              return tipoNotificacao;
       }
}
