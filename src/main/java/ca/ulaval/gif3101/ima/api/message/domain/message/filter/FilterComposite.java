package ca.ulaval.gif3101.ima.api.message.domain.message.filter;

public interface FilterComposite extends Filter {

    void addFilter(Filter filter);

}
