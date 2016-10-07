/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.UsuariosRolesFacadeLocal;
import com.sv.udb.modelo.UsuariosRoles;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Alexander
 */
@Named(value = "usuariosRolesBean")
@RequestScoped
public class UsuariosRolesBean implements Serializable {

    @EJB
    private UsuariosRolesFacadeLocal FCDEUsuaRole;
    private UsuariosRoles objeUsuaRole;
    private List<UsuariosRoles> listUsuaRole;
    private boolean guardar;

    public UsuariosRoles getObjeUsuaRole() {
        return objeUsuaRole;
    }

    public void setObjeUsuaRole(UsuariosRoles objeUsuaRole) {
        this.objeUsuaRole = objeUsuaRole;
    }

    public List<UsuariosRoles> getListUsuaRole() {
        return listUsuaRole;
    }

    public void setListUsuaRole(List<UsuariosRoles> listUsuaRole) {
        this.listUsuaRole = listUsuaRole;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }

    /**
     * Creates a new instance of UsuariosRolesBean
     */
    public UsuariosRolesBean() {
    }

    @PostConstruct
    public void init() {
        this.limpForm();
        this.consTodo();
    }

    public void limpForm() {
        this.objeUsuaRole = new UsuariosRoles();
        this.guardar = true;
    }

    public void consTodo() {
        try {
            this.listUsuaRole = FCDEUsuaRole.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void guar() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try {
            if (objeUsuaRole.getCodiRole() != null && objeUsuaRole.getCodiUsua() != null) {
                Calendar Calendario = new GregorianCalendar().getInstance();
                Date Fecha = Calendario.getTime();
                SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
                String FechaAlta = formatoDeFecha.format(Fecha);
                try {
                    objeUsuaRole.setFechAltaRole(formatoDeFecha.parse(FechaAlta));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                objeUsuaRole.setEstaUsuaRole(1);
                FCDEUsuaRole.create(this.objeUsuaRole);
                this.listUsuaRole.add(this.objeUsuaRole);
                this.limpForm();
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
            }else{
                ctx.execute("setMessage('MESS_ERRO', 'Atención', 'No ha seleccionado un rol o un usuario')");
            }
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar')");
        }
    }

    public void modi() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try {
            if (this.objeUsuaRole.getCodiUsuaRole() == null) {
                ctx.execute("setMessage('MESS_ERRO', 'Atención', 'No ha seleccionado un dato')");
            } else {
                FCDEUsuaRole.edit(this.objeUsuaRole);
                this.listUsuaRole = FCDEUsuaRole.findAll();
                limpForm();
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
            }
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar')");
        }
    }

    public void elim() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try {
            if (this.objeUsuaRole.getCodiUsuaRole() == null) {
                ctx.execute("setMessage('MESS_ERRO', 'Atención', 'No ha seleccionado un dato')");
            } else {
                Calendar Calendario = new GregorianCalendar().getInstance();
                Date Fecha = Calendario.getTime();
                SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
                String FechaBaja = formatoDeFecha.format(Fecha);
                try {
                    objeUsuaRole.setFechBajaRole(formatoDeFecha.parse(FechaBaja));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                objeUsuaRole.setEstaUsuaRole(0);
                FCDEUsuaRole.edit(this.objeUsuaRole);
                this.listUsuaRole = FCDEUsuaRole.findAll();
                this.limpForm();
                ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
            }
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
        }
    }

    public void cons() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiUsuaRolePara"));
        try {
            this.objeUsuaRole = FCDEUsuaRole.find(codi);
            this.guardar = false;
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
    }
}
