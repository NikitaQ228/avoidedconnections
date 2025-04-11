package ru.avoidedconnections.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avoidedconnections.repository.JwtBlacklistRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtBlacklistCleaner {

    @Autowired
    private JwtBlacklistRepository jwtBlacklistRepository;

    @Scheduled(fixedDelay = 3600000)
    @Transactional
    public void cleanBlacklist() {
        Instant now = Instant.now();
        jwtBlacklistRepository.deleteAllByExpirationDateBefore(now);
    }
}