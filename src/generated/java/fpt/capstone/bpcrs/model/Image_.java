package fpt.capstone.bpcrs.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Image.class)
public abstract class Image_ extends fpt.capstone.bpcrs.component.Auditing_ {

	public static volatile SingularAttribute<Image, Car> car;
	public static volatile SingularAttribute<Image, String> link;

	public static final String CAR = "car";
	public static final String LINK = "link";

}

