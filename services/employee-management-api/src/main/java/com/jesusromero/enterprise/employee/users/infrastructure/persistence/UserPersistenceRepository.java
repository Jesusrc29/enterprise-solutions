package com.jesusromero.enterprise.employee.users.infrastructure.persistence;

import com.jesusromero.enterprise.employee.users.domain.model.User;
import com.jesusromero.enterprise.employee.users.domain.repository.UserRepository;
import com.jesusromero.enterprise.employee.users.infrastructure.persistence.mapper.UserPersistenceMapper;
import com.jesusromero.enterprise.employee.users.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserPersistenceRepository implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    public UserPersistenceRepository(
            UserJpaRepository userJpaRepository,
            UserPersistenceMapper userPersistenceMapper
    ) {
        this.userJpaRepository = userJpaRepository;
        this.userPersistenceMapper = userPersistenceMapper;
    }

    @Override
    public User save(User user) {
        return userPersistenceMapper.toDomain(
                userJpaRepository.save(userPersistenceMapper.toEntity(user))
        );
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id)
                .map(userPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(userPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userPersistenceMapper::toDomain);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userJpaRepository.findAll(pageable)
                .map(userPersistenceMapper::toDomain);
    }
}
