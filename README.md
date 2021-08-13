# Relay Function
`Relay Function` is just another java `@FunctionalInterface` that makes organizing sequential independent business logics extremely convenient.

# Example System
In our over simplified hypothetical system, we create and update `User`. It has following properties,

* Full name
* Email
* Activation Date
* Expiration Date

Obviously there are a bunch of validation rules before actually persisting the data. The validation rules looks as following.

## Rules for creating a user
* Name is not empty
* Email address must be valid
* Email address must be unique
* Name should only contain alphanum
* Activation date must exist
* Expiration date should not exist

## Rule for updating a user
* Email address can not be changed.
* Name is not empty
* Activation date can not be changed.
* Activation date must exist
* Expiration date can not be before existing activation date.

These are pretty much a basic set of rules that would be applicable for any decent application.
Couple of important points to note here,
* Few of them are common for both create and update scenarios, like
  * Name is not empty
  * Activation date must exist
* Some of them needs only the incoming User object, for instance,
  * Name is not empty.
  * Email address must be valid.
* Whereas some of them needs both incoming user object and persisted user object.
  * Activation date can not be changed. 
  * Expiration date can not be before existing activation date.

So we are going to create a UserValidator class that will contain all these logics and provide convenient public method
to execute them. Let's list out characteristics we want of the validator class.

* Each rule should be exclusive, a single unit of logic.
* The logics should be able to chained together
* Rules are individually unit testable.
* When validation fails, throw exception, otherwise pass it along. Nothing to return.
* Exception should clearly identify where the validation failed.

Ensuring all the points above should yield a very readable and manageable design of the `UserValidator` class.
We will see how Relay Function can help us to achieve this.

# Implementation

## End Goal
In the end the rules will be defined like below,
```
    // Create user
    nameIsNotEmpty()
            .next(emailAddressIsValid())
            .next(emailAddressMustBeUnique())
            .next(nameMustHaveAlphaNums())
            .next(activationDateMustExist())
            .next(expirationDateShouldNotExist())

```
```
    // Update user
    emailCannotBeChanged()
            .next(nameIsNotEmpty())
            .next(activationDateCannotBeChanged())
            .next(activationDateMustExist())
            .next(expirationDateCannotBeBeforeActivationDate())

```
The way the rules are organized are pretty much same as we listed them above.

## Relay Function
Following are the functional interfaces called RelayOne and RelayTwo. As the name implies they are simply designed 
to relay the object T or U down the chain of functions.
``` 
@FunctionalInterface
public interface RelayOne<T> {
    void apply(T t);
	default RelayOne<T> next(RelayOne<T> nextfn) {...}
	default RelayTwo<T> next(RelayTwo<T> nextfn) {...}
}
```
The `next` functions allows you to connect any two relay functions.
``` 
@FunctionalInterface
public interface RelayTwo<T, U> {
    void apply(T t, U u);
	default RelayTwo<T> next(RelayTwo<T> nextfn) {...}
	default RelayTwo<T> next(RelayOne<T> nextfn) {...}
}
```

## Type Aliasing
To enhance the code readability, we can define type aliases for our `User` model. 
So instead of using `RelayOne<User>` we can call it `SingleUserRule` and
instead of using `RelayTwo<User, User>` we can call it `TwoUsersRule`. Obviously possible to come up with better names 
but here the main goal was to make their purpose clear.

```
public interface SingleUserRule extends RelayOne<User> {
	default SingleUserRule next(SingleUserRule nextFn) {...}	
	default SingleUserRule next(TwoUsersRule nextFn) {...}
}
```
```
public interface TwoUsersRule extends RelayTwo<User, User> {
    default TwoUsersRule next(TwoUsersRule nextFn) {...}
    default TwoUsersRule next(SingleUserRule nextFn) {...}
}

```

## Implementing a rule
Following function implements the unique email address rule. It needs the new data coming as input
```	
	SingleUserRule nameIsNotEmpty() {
		return (user) -> {
			if(user.getFullName() == null || user.getFullName().isBlank()) {
				throw new UserValidationException("User name must exists");
			}
		};
	}
```

```	
	TwoUsersRule activationDateCannotBeChanged() {
		return (newUser, oldUser) -> {
			if(!newUser.getActivationDate().equals(oldUser.getActivationDate())) {
				throw new UserValidationException("Activation date must not be changed");
			}
		};
	}
```

## Test
Look for `UserValidatorTest.java` to see individual business logics are testable.  
Look in `UserServiceTest.java` for some basic tests for UserService.


