package ca.ulaval.gif3101.ima.api.domain.message.filter;

public interface FilterComposite extends Filter {

    void addFilter(Filter filter);

}
