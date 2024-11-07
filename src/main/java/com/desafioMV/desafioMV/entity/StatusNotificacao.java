package com.desafioMV.desafioMV.entity;

public enum StatusNotificacao {

        ENTREGUE("Entregue"),
        LIDA("Lida"),
        AGENDADA("Agendada"),
        CANCELADO("Cancelado");

        private String status;


        private StatusNotificacao(String status){
            this.status=status;
        }

        public String getStatusNotificacao(){
            return status;
        }
        
    
}
