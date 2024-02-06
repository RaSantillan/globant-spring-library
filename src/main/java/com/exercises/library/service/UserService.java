package com.exercises.library.service;

import com.exercises.library.entity.Image;
import com.exercises.library.entity.User;
import com.exercises.library.enumeration.Role;
import com.exercises.library.exception.MyException;
import com.exercises.library.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Autowired
    public UserService(UserRepository userRepository, ImageService imageService) {
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    @Transactional
    public void registrar(
            String name, String email, String password, String password2, MultipartFile file) throws MyException {
        validar(name, email, password, password2);
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole(Role.USER);

        Image image = imageService.save(file);
        user.setImage(image);

        userRepository.save(user);
    }

    @Transactional
    public void update(
            String id, String name, String email, String password, String password2, MultipartFile file
    ) throws MyException {
        validar(name, email, password, password2);

        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {

            User usuario = response.get();
            usuario.setName(name);
            usuario.setEmail(email);

            usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            usuario.setRole(Role.USER);

            String idImage = null;

            if (usuario.getImage() != null) {
                idImage = usuario.getImage().getId();
            }

            Image image = imageService.update(file, idImage);

            usuario.setImage(image);

            userRepository.save(usuario);
        }
    }

    private void validar(String nombre, String email, String password, String password2) throws MyException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MyException("el nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MyException("el email no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MyException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MyException("Las contraseñas ingresadas deben ser iguales");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.searchByEmail(email);
        if (user != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+user.getRole().toString());
            permisos.add(grantedAuthority);

            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attributes.getRequest().getSession(true);
            session.setAttribute("usuariosession", user);

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), permisos);
        } else {
            return null;
        }
    }
}
