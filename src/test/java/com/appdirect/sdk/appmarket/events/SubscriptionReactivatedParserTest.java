package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.EventHandlingContexts.defaultEventContext;
import static com.appdirect.sdk.appmarket.events.EventHandlingContexts.eventContext;
import static com.appdirect.sdk.appmarket.events.EventFlag.DEVELOPMENT;
import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.util.Arrays.array;

import org.junit.Test;

public class SubscriptionReactivatedParserTest {
	private SubscriptionReactivatedParser parser = new SubscriptionReactivatedParser();

	@Test
	public void parse_extractsTheConsumerKey() throws Exception {
		SubscriptionReactivated parsedEvent = parser.parse(someEvent(), eventContext("the-key"));

		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo("the-key");
	}

	@Test
	public void parse_extractsTheAccountInfo() throws Exception {
		SubscriptionReactivated parsedEvent = parser.parse(someEventFor("big-account").build(), defaultEventContext());

		assertThat(parsedEvent.getAccountInfo().getAccountIdentifier()).isEqualTo("big-account");
	}

	@Test
	public void parse_extractsTheDevelopmentFlag() throws Exception {
		SubscriptionReactivated parsedEvent = parser.parse(someEventFor("big-account").flag(DEVELOPMENT).build(), defaultEventContext());

		assertThat(parsedEvent.isDevelopment()).isTrue();
	}

	@Test
	public void parse_extractsTheQueryParams() throws Exception {
		SubscriptionReactivated parsedEvent = parser.parse(someEvent(), eventContext("key", oneQueryParam("hello", "the")));

		assertThat(parsedEvent.getQueryParameters()).containsOnly(entry("hello", array("the")));
	}

	private EventInfo someEvent() {
		return someEventFor("some-account").build();
	}

	private EventInfo.EventInfoBuilder someEventFor(String accountId) {
		return EventInfo.builder().payload(EventPayload.builder().account(AccountInfo.builder().accountIdentifier(accountId).build()).build());
	}
}