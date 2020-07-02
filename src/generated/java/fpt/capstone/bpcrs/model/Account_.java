package fpt.capstone.bpcrs.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Account.class)
public abstract class Account_ extends fpt.capstone.bpcrs.component.Auditing_ {

	public static volatile SingularAttribute<Account, Role> role;
	public static volatile SingularAttribute<Account, String> imageUrl;
	public static volatile SingularAttribute<Account, String> carLicensePlate;
	public static volatile SingularAttribute<Account, Boolean> active;
	public static volatile SingularAttribute<Account, String> fullName;
	public static volatile SingularAttribute<Account, String> email;

	public static final String ROLE = "role";
	public static final String IMAGE_URL = "imageUrl";
	public static final String CAR_LICENSE_PLATE = "carLicensePlate";
	public static final String ACTIVE = "active";
	public static final String FULL_NAME = "fullName";
	public static final String EMAIL = "email";

}

