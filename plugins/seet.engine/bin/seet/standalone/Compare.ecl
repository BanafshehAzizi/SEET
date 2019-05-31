post {
	var result : Boolean = true;
	for (p in SMF!EPackage)
	{
		if (not System.context.matchTrace.reduced.matches.select(m|m.left.isTypeOf(SMF!EPackage)).collect(m|m.left.name).includes(p.name))
			result = false;			
	}
	for (p in Expected!EPackage)
	{
		if (not System.context.matchTrace.reduced.matches.select(m|m.right.isTypeOf(Expected!EPackage)).collect(m|m.right.name).includes(p.name))
			result = false;			
	}
	var interfaceRule : new Native("seet.layout.interfaceRule");
	interfaceRule.showFinalResult(result);
}

rule EPackage2EPackage
	match s : SMF!EPackage
	with t: Expected!EPackage
	{
	
	compare : s.name == t.name and
			s.eClassifiers.matches(t.eClassifiers) and
			s.eSubPackages.matches(t.eSubPackages)  and
			s.eAnnotations.matches(t.eAnnotations)
}

@lazy
rule EClass2EClass
	match s : SMF!EClass
	with t: Expected!EClass
	{
	
	compare : s.name = t.name and
			s.EAttributes.matches(t.EAttributes) and
			s.EReferences.matches(t.EReferences)
}

@lazy
rule EAttribute2EAttribute
	match s : SMF!EAttribute
	with t : Expected!EAttribute
	{

	compare : s.name = t.name and
			s.defaultValueLiteral = t.defaultValueLiteral	
}

@lazy
rule EReference2EReference
	match s : SMF!EReference
	with t : Expected!EReference
	{
	compare : s.name = t.name and
			s.containment = t.containment and
			s.lowerBound = t.lowerBound and
			s.upperBound = t.upperBound	
}

@lazy
rule EEnum2EEnum
	match s : SMF!EEnum
	with t: Expected!EEnum
	{
	compare : s.eLiterals.matches(t.eLiterals)
}

@lazy
rule EEnumLiteral2EEnumLiteral
	match s : SMF!EEnumLiteral
	with t : Expected!EEnumLiteral
	{
	compare : s.name = t.name and
			s.literal = t.literal	
}