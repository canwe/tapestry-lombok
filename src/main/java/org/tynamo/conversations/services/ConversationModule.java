package org.tynamo.conversations.services;

import java.io.IOException;
import java.util.Properties;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;

public class ConversationModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(RequestHandlerDecorator.class, RequestHandlerDecoratorImpl.class);
		binder.bind(ConversationManager.class, ConversationManagerImpl.class);
	}

	public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration) {
		configuration.add(new LibraryMapping("conversation", "org.tynamo.conversations"));
	}

	public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration) {
		configuration.add("tynamo-conversations-" + "0.1.0", "org/tynamo/conversations");
	}

	public static void contributePersistentFieldManager(MappedConfiguration<String, PersistentFieldStrategy> configuration, RequestGlobals requestGlobals,
			Request request, ConversationManager conversationManager) {
		configuration.add("conversation", new ConversationalPersistentFieldStrategy(request, conversationManager));
	}

	public static <T> T decorateComponentRequestHandler(Class<T> serviceInterface, T delegate, RequestHandlerDecorator decorator) {
		return decorator.build(serviceInterface, delegate);
	}

}