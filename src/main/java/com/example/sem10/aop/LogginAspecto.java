package com.example.sem10.aop;

import com.example.sem10.domain.entities.Auditoria;
import com.example.sem10.domain.entities.Curso;
import com.example.sem10.domain.entities.Alumno;
import com.example.sem10.domain.persistence.AuditoriaDao;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Calendar;

@Component
@Aspect
public class LogginAspecto {
    private Long tx;

    @Autowired
    private AuditoriaDao auditoriaDao;


    @Around("execution(* com.example.sem10.services.*ServiceImpl.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result =  null;
        long currTime = System.currentTimeMillis();
        tx = System.currentTimeMillis();
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String metodo = "tx[" + tx + "] - " + joinPoint.getSignature().getName();
        //logger.info(metodo + "()");
        if(joinPoint.getArgs().length > 0)
            logger.info("() INPUT:", metodo, Arrays.toString(joinPoint.getArgs()));
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
        logger.info(metodo + "(): tiempo transcurrido " + (System.currentTimeMillis() - currTime) + " ms.");
        return result;
    }

    @After("execution(* com.example.sem10.controllers.*Controller.guardar*(..)) ||" +
            "execution(* com.example.sem10.controllers.*Controller.editar*(..)) ||" +
            "execution(* com.example.sem10.controllers.*Controller.eliminar*(..))")
    public void auditoria(JoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String metodo = joinPoint.getSignature().getName();
        Integer id = null;

        Object[] parametros = joinPoint.getArgs();
        String entidad = "";

        if (metodo.startsWith("guardar")) {
            if (parametros[0] instanceof Curso) {
                Curso curso = (Curso) parametros[0];
                id = curso.getId();
                entidad = "cursos";
            } else if (parametros[0] instanceof Alumno) {
                Alumno alumno = (Alumno) parametros[0];
                id = alumno.getId();
                entidad = "alumnos";
            }
        } else if (metodo.startsWith("editar") || metodo.startsWith("eliminar")) {
            id = (Integer) parametros[0];
            entidad = parametros[0] instanceof Curso ? "cursos" : "alumnos";
        }

        String traza = "tx[" + tx + "] - " + metodo;
        logger.info(traza + "(): registrando auditoria...");
        auditoriaDao.save(new Auditoria(entidad, id, Calendar.getInstance().getTime(), "usuario", metodo));
    }

}

