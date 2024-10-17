package com.nighthawk.spring_portfolio.Slack;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackMessageRepository extends JpaRepository<SlackMessage, Long> {
}
