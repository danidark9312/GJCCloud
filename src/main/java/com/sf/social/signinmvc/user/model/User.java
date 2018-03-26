package com.sf.social.signinmvc.user.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.maestros.entities.TipoDocumento;
import com.gja.gestionCasos.reportes.entities.Justificacion;
import com.sf.roles.RolesUsuarios;
import com.sf.social.signinmvc.common.model.BaseEntity;

/**
 * @author Petri Kainulainen
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
public class User extends BaseEntity<String> {

	@Id
    @Basic(optional = false)
    @Column(name = "CDUSUARIO")
    private String id;

    @Column(name = "DSEMAIL", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "DSNOMBRE", length = 100,nullable = false)
    private String nombre;

    @Column(name = "DSAPELLIDO", length = 100, nullable = false)
    private String apellido;

    @Column(name = "password", length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "DSROL", length = 20, nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "sign_in_provider", length = 20)
    private SocialMediaService signInProvider;
    
    @Basic(optional = false)
    @Column(name = "SNACTIVO")
    private String activo;
    @Column(name = "DSFOTO")
    private String foto;
    
    // Número tarjeta profesional de los usuarios que son profesionales
    @Column(name = "NMTARJETAPROFESIONAL")
    private String numeroTarjetaProfesional;
    // Número de telefono de los usuarios
    @Column(name = "NMTELEFONO")
    private String numeroTelefono;
    
    @Column(name = "DSDIRECCION")
    private String direccion;
    
    @JsonIgnore
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<EquipoCaso> EquipoCasoSet;
    
    @Column(name = "DSINICIALESABOGADO")
    private String abreviacionAbogado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<RolesUsuarios> rolesUsuariosList;    
    @Column(name = "NMCELULAR")
    private String celular;
    
    @Column(name = "FECNACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nacimientoUsuario;
    
    @JoinColumn(name = "TIPODOCUMENTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TipoDocumento tipodocumento;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioModificacion")
    private List<Justificacion> justificacionesList;
    
//    @Transient
//    private String fechaNacimientoString;
//    
//    
//    public String getFechaNacimientoString() {
//		return fechaNacimientoString;
//	}
//
//	public void setFechaNacimientoString(String fechaNacimientoString) {
//		this.fechaNacimientoString = fechaNacimientoString;
//	}

	public List<Justificacion> getJustificacionesList() {
		return justificacionesList;
	}

	public void setJustificacionesList(List<Justificacion> justificacionesList) {
		this.justificacionesList = justificacionesList;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public User() {

    }
    
    public User(String id) {
    	this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    @Override
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getPassword() {
        return password;
    }    

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
        return role;
    }

    public SocialMediaService getSignInProvider() {
        return signInProvider;
    }
    
    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    public String getNumeroTarjetaProfesional() {
		return numeroTarjetaProfesional;
	}

	public void setNumeroTarjetaProfesional(String numeroTarjetaProfesional) {
		this.numeroTarjetaProfesional = numeroTarjetaProfesional;
	}
    public String getNumerocelular() {
		return celular;
	}
    public void setNumerocelular(String celular) {
		this.celular = celular;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getAbreviacionAbogado() {
		return abreviacionAbogado;
	}

	public void setAbreviacionAbogado(String abreviacionAbogado) {
		this.abreviacionAbogado = abreviacionAbogado;
	}
	
	public Date getNacimientoUsuario() {
		return nacimientoUsuario;
	}

	public void setNacimientoUsuario(Date nacimientoUsuario) {
		this.nacimientoUsuario = nacimientoUsuario;
	}

	public List<EquipoCaso> getEquipoCasoSet() {
		return EquipoCasoSet;
	}

	public void setEquipoCasoSet(List<EquipoCaso> equipoCasoSet) {
		EquipoCasoSet = equipoCasoSet;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public List<RolesUsuarios> getRolesUsuariosList() {
		return rolesUsuariosList;
	}

	public void setRolesUsuariosList(List<RolesUsuarios> rolesUsuariosList) {
		this.rolesUsuariosList = rolesUsuariosList;
	}

	public TipoDocumento getTipoDocumento() {
        return tipodocumento;
    }

    public void setTipoDocumento(TipoDocumento tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

		@Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("creationTime", this.getCreationTime())
                .append("email", email)
                .append("nombre", nombre)
                .append("apellido", apellido)
                .append("modificationTime", this.getModificationTime())
                .append("signInProvider", this.getSignInProvider())
                .append("version", this.getVersion())
                .append("activo", this.getActivo())
                .append("foto", this.getFoto())
                .append("numeroTarjetaProfesional", numeroTarjetaProfesional)
                .append("numeroTelefono", numeroTelefono)
                .append("celular", celular)
                .append("direccion", direccion)                
                .append("abreviacionAbogado", abreviacionAbogado)
//                .append("tipoDocumento", tipodocumento)
                .toString();
    }

    public static class Builder {

        private User user;

        public Builder() {
            user = new User();
            user.role = Role.ROLE_USER;
        }

        public Builder email(String email) {
            user.email = email;
            return this;
        }

        public Builder nombre(String nombre) {
            user.nombre = nombre;
            return this;
        }

        public Builder apellido(String apellido) {
            user.apellido = apellido;
            return this;
        }

        public Builder password(String password) {
            user.password = password;
            return this;
        }

        public Builder signInProvider(SocialMediaService signInProvider) {
            user.signInProvider = signInProvider;
            return this;
        }
        
        public Builder activo(String activo) {
            user.activo = activo;
            return this;
        }
        
        public Builder foto(String foto) {
            user.foto = foto;
            return this;
        }
        
        public Builder numeroTarjetaProfesional(String numeroTarjetaProfesional) {
            user.numeroTarjetaProfesional = numeroTarjetaProfesional;
            return this;
        }
        
        public Builder numeroTelefono(String numeroTelefono) {
            user.numeroTelefono = numeroTelefono;
            return this;
        }
        public Builder direccion(String direccion) {
            user.direccion = direccion;
            return this;
        }                       
        public Builder abreviacionAbogado(String abreviacionAbogado) {
            user.abreviacionAbogado = abreviacionAbogado;
            return this;
        }               
//        public Builder tipoDocumento(TipoDocumento tipoDocumento) {
//            user.tipodocumento = tipoDocumento;
//            return this;
//        }
        public User build() {
            return user;
        }
        
    }
}
