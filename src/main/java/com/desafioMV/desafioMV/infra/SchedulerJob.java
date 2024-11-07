package com.desafioMV.desafioMV.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.desafioMV.desafioMV.controller.NotificacaoEmailAdminController;

@Configuration
@EnableScheduling
public class SchedulerJob {
    
    @Autowired
    NotificacaoEmailAdminController notificacaoEmailAdminController;

    @Scheduled(fixedRate = 60000)
    @Async
	public void job(){
		notificacaoEmailAdminController.enviarNotificacaoAgendada();
	}
}
