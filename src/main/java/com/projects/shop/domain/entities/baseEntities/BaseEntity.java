package com.projects.shop.domain.entities.baseEntities;

import javax.persistence.MappedSuperclass;

/**
 * Best practises: BaseUuidEntity to extend BaseEntity which will implement equals() and hasCode()
 *
 * @see <a href="https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate"></a>
 */

//TODO: Provide logic for these methods

@MappedSuperclass
public abstract class BaseEntity {

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
