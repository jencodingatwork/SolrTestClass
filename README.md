This is a project to illustrate the error I am getting in my Solr Unit tests:

java.lang.Exception: Assertions mismatch: -ea was not specified but -Dtests.asserts=true
	at __randomizedtesting.SeedInfo.seed([5B25E606A72BD541]:0)
	at org.apache.lucene.util.TestRuleAssertionsRequired$1.evaluate(TestRuleAssertionsRequired.java:47)
	at org.apache.lucene.util.TestRuleMarkFailure$1.evaluate(TestRuleMarkFailure.java:47)
	at org.apache.lucene.util.TestRuleIgnoreAfterMaxFailures$1.evaluate(TestRuleIgnoreAfterMaxFailures.java:64)
	at org.apache.lucene.util.TestRuleIgnoreTestSuites$1.evaluate(TestRuleIgnoreTestSuites.java:54)
	at com.carrotsearch.randomizedtesting.rules.StatementAdapter.evaluate(StatementAdapter.java:36)
	at com.carrotsearch.randomizedtesting.ThreadLeakControl$StatementRunner.run(ThreadLeakControl.java:367)
	at java.lang.Thread.run(Thread.java:745)
