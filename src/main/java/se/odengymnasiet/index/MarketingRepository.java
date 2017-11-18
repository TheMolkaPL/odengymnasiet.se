package se.odengymnasiet.index;

import se.odengymnasiet.Repository;

import java.util.Collection;

public interface MarketingRepository extends Repository<Marketing> {
    Collection<Marketing> findAllDeployed();
}
