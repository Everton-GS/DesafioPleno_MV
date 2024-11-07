package com.desafioMV.desafioMV.entity;

public enum RoleUsuario {
        USUARIO("ROLE_Usuario"),
        ADMINISTRADOR("ROLE_Administrador");

        private String tipo;

        private RoleUsuario(String tipo) {
            this.tipo = tipo;
        }

        public String getTipo() {
            return tipo;
        }

        
}
