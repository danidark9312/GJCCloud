package com.sf.social.signinmvc.security.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

import com.sf.social.signinmvc.user.model.Role;
import com.sf.social.signinmvc.user.model.SocialMediaService;
import com.sf.social.signinmvc.user.model.User;
import com.sf.social.signinmvc.user.model.User.Builder;

/**
 * @author Petri Kainulainen
 */
public class SocialUserDetails extends SocialUser {

    private String id;

    private String nombre;

    private String apellido;

    private Role role;

    private SocialMediaService socialSignInProvider;
    
    private String activo;
    
    private String foto;
    
    private String numeroTarjetaProfesional;
       
    private String numeroTelefono;
    
    private String direccion;
    
    
    public SocialUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Role getRole() {
        return role;
    }

    public SocialMediaService getSocialSignInProvider() {
        return socialSignInProvider;
    }
    
    
    
    public String getActivo() {
		return activo;
	}

	public String getFoto() {
		return foto;
	}

	public String getNumeroTarjetaProfesional() {
		return numeroTarjetaProfesional;
	}

	public void setNumeroTarjetaProfesional(String numeroTarjetaProfesional) {
		this.numeroTarjetaProfesional = numeroTarjetaProfesional;
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
	
	@Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("username", getUsername())
                .append("nombre", nombre)
                .append("apellido", apellido)
                .append("role", role)
                .append("socialSignInProvider", socialSignInProvider)
                .append("activo", this.getActivo())
                .append("foto", this.getFoto())
                .append("numeroTarjetaProfesional", numeroTarjetaProfesional)
                .append("numeroTelefono", numeroTarjetaProfesional)
                .append("direccion", direccion)
                .toString();
    }

    public static class Builder {

        private String id;

        private String username;

        private String nombre;

        private String apellido;

        private String password;

        private Role role;

        private SocialMediaService socialSignInProvider;
        
        private String activo;
        
        private String foto;
        
        private Integer piso;
        
        private String numeroTarjetaProfesional;
        
        private String numeroTelefono;
        
        private String direccion;
        
        private Set<GrantedAuthority> authorities;

        public Builder() {
            this.authorities = new HashSet<GrantedAuthority>();
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder apellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Builder password(String password) {
            if (password == null) {
                password = "SocialUser";
            }

            this.password = password;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
            this.authorities.add(authority);

            return this;
        }

        public Builder socialSignInProvider(SocialMediaService socialSignInProvider) {
            this.socialSignInProvider = socialSignInProvider;
            return this;
        }
        
        
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        
        public Builder activo(String activo) {
            this.activo = activo;
            return this;
        }
        
        public Builder foto(String foto) {
            this.foto = foto;
            return this;
        }
        
        public Builder piso(Integer piso) {
            this.piso = piso;
            return this;
        }
        public Builder numeroTarjetaProfesional(String numeroTarjetaProfesional) {
            this.numeroTarjetaProfesional = numeroTarjetaProfesional;
            return this;
        }
        
        public Builder numeroTelefono(String numeroTelefono) {
            this.numeroTelefono = numeroTelefono;
            return this;
        }
        public Builder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public SocialUserDetails build() {
            SocialUserDetails user = new SocialUserDetails(username, password, authorities);

            user.id = id;
            user.nombre = nombre;	
            user.apellido = apellido;
            user.role = role;
            user.socialSignInProvider = socialSignInProvider;
            user.activo=activo;
            user.foto=foto;
            user.numeroTarjetaProfesional=numeroTarjetaProfesional;
            user.numeroTelefono=numeroTelefono;
            user.direccion=direccion;
            return user;
        }
    }
}
