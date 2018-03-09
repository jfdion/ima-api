package ca.ulaval.gif3101.ima.api.infrastructure.message.filter;

public interface MessageFilterComposite extends MessageFilter {

    void addFilter(MessageFilter filter);

}
