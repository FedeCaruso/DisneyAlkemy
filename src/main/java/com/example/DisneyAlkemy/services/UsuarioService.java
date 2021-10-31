package com.example.DisneyAlkemy.services;

import com.example.DisneyAlkemy.exceptions.WebException;
import com.example.DisneyAlkemy.models.Usuario;
import com.example.DisneyAlkemy.repositories.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Fede
 */
@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario save(String username, String password, String password2) throws WebException {
        Usuario usuario = new Usuario();

        if (username == null | username.isEmpty()) {
            throw new WebException("El username no puede estar vacio");
        }
        if (findByUsername(username) != null) {
            throw new WebException("El username que queres usar ya existe.");
        }
        if (password == null | password2 == null | password.isEmpty() | password2.isEmpty()) {
            throw new WebException("La contraseña no puede estar vacía");
        }
        if (!password.equals(password2)) {
            throw new WebException("Las contraseñas deben ser iguales.");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        usuario.setUsername(username);
        usuario.setClave(encoder.encode(password));

        return usuarioRepository.save(usuario);
    }

    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public List<Usuario> listAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Usuario usuario = usuarioRepository.findByUsername(username);
            User user;
            List<GrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority("ROLE_"+usuario.getRol()));
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            
            return new User(username, usuario.getClave(), authorities);
        } catch (Exception e) {
            throw new UnsupportedOperationException("El usuario no existe.");
        }
    }
}
