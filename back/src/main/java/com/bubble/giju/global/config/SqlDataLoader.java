package com.bubble.giju.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@Profile("prod")
public class SqlDataLoader {

    private final DataSource dataSource;

    public SqlDataLoader(DataSource dataSource) {
        this.dataSource = dataSource;
        log.info("SqlDataLoader 생성됨 - DataSource: {}", dataSource.getClass().getSimpleName());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() throws Exception {
        log.info("=== SqlDataLoader 실행 시작 ===");

        List<String> sqlFiles = List.of(
                "sql/userData.sql",
                "sql/drinkData.sql",
                "sql/images.sql",
                "sql/orderData.sql",
                "sql/paymentData.sql",
                "sql/likesData.sql",
                "sql/reviews.sql"
        );

        //DB커넥션 연결
        try (Connection conn = dataSource.getConnection()) {
            log.info("데이터베이스 연결 성공: {}", conn.getMetaData().getURL());

            for (String path : sqlFiles) {
                log.info("SQL 파일 처리 시작: {}", path);

                try {
                    ClassPathResource resource = new ClassPathResource(path);

                    if (!resource.exists()) {
                        log.warn("SQL 파일이 존재하지 않음: {}", path);
                        continue;
                    }

                    String sql = new BufferedReader(new InputStreamReader(resource.getInputStream()))
                            .lines().collect(Collectors.joining("\n"));

                    if (sql.trim().isEmpty()) {
                        log.warn("SQL 파일이 비어있음: {}", path);
                        continue;
                    }

                    String[] statements = sql.split(";");
                    log.info("파일 {} 에서 {} 개의 SQL 문장 발견", path, statements.length);

                    int executedCount = 0;
                    for (String statement : statements) {
                        statement = statement.trim();
                        if (!statement.isEmpty()) {
                            try (Statement stmt = conn.createStatement()) {
                                log.debug("SQL 실행: {}", statement.length() > 100 ?
                                        statement.substring(0, 100) + "..." : statement);
                                stmt.execute(statement);
                                executedCount++;
                            } catch (Exception e) {
                                log.error("SQL 실행 실패: {}", statement, e);
                                throw e;
                            }
                        }
                    }

                    log.info("파일 {} 실행 완료 - {} 개 SQL 문장 실행됨", path, executedCount);

                } catch (Exception e) {
                    log.error("파일 {} 처리 중 오류 발생", path, e);
                    throw e;
                }
            }

            log.info("=== SqlDataLoader 실행 완료 ===");

        } catch (Exception e) {
            log.error("데이터베이스 연결 또는 SQL 실행 중 오류 발생", e);
            throw e;
        }
    }
}
