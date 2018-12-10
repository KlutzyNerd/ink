package xyz.itao.ink.repository;

import xyz.itao.ink.domain.MetaDomain;

/**
 * @author hetao
 * @date 2018-12-10
 * @description
 */
public interface MetaRepository {
    MetaDomain updateMetaDomain(MetaDomain domain);

    MetaDomain saveNewMetaDomain(MetaDomain domain);
}
