package com.prezi.gradle.pride.cli

/**
 * Created by lptr on 15/04/14.
 */
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply

/**
 * Filters events above the threshold level.
 *
 * Events with a level above the specified
 * level will be denied, while events with a level
 * equal or below the specified level will trigger a
 * FilterReply.NEUTRAL result, to allow the rest of the
 * filter chain process the event.
 */
public class LogbackHighTresholdFilter extends Filter<ILoggingEvent> {

	private Level level

	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (!isStarted()) {
			return FilterReply.NEUTRAL
		}

		if (level.isGreaterOrEqual(event.level)) {
			return FilterReply.NEUTRAL
		} else {
			return FilterReply.DENY
		}
	}

	public void setLevel(String level) {
		this.level = Level.toLevel(level)
	}

	public void start() {
		if (this.level != null) {
			super.start()
		}
	}
}
