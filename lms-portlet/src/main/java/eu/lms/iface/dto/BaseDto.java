package eu.lms.iface.dto;

/**
 *
 * @author Lubomir Lacika
 * Data Transfer Object for BaseEntity entity.
 */
public abstract class BaseDto<T> {

    public abstract T getId();

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseDto other = (BaseDto) obj;
        if (this.getId() != other.getId() && (this.getId() == null || !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [id = " + String.valueOf(getId()) + "]";
    }
}
