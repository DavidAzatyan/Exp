/*
  The data server query grammar

  ts([SUM|AVG], [metric], [tag pairs...])
  rate([SUM|AVG], [metric], [tag pairs...])
  events([tag pairs])
  math expressions
  spans([span] [and duration] [and tag pairs...])
*/
grammar DSQuery;

import DSLexer;

@header {
  package queryserver.parser;
}

@lexer::members {
  private Token lastToken = null;
  private Token last2Token = null;

  @Override
  public Token nextToken() {
      Token result = super.nextToken();
      if (result.getChannel() != Token.HIDDEN_CHANNEL) {
        this.last2Token = lastToken;
        this.lastToken = result;
      }
      return result;
  }

  public boolean isRegExEnabled() {
      if(lastToken == null)
        return true;

      String type = lastToken.getText();
      String previousType = (last2Token == null) ? null : last2Token.getText();
      // the token before a valid regex can only be the following ones
      return (previousType == null || !previousType.equals("raw")) && type != null &&
      (type.equals("(") || type.equals("=") ||type.equals(",") || type.equals("and") ||
      type.equals("AND") || type.equals("or") || type.equals("OR") || type.equals("not") ||
      type.equals("NOT"));
  }
}

program
  : query EOF
  ;

query
  : expression
  | eventsexpression
  | histexpression
  | spanExpression
  | traceExpression
  | edgesExpression
  ;

// HISTOGRAMS
histexpression
  : '(' histexpression ')'
  | histexpression set=('as'|'AS') variableName
  | getter
  | histseries
  | merge
  | histalign
  | histdebug
  | bucket
  | cumulativeBucket
  | frequencyBucket
  | histalias
  | histimpersonate
  | histglobalFilter
  | histLimit
  ;

// -> histexpression
histseries
  : 'hs' '(' tsexpression (',' tsexpression)* ')'
  ;

merge
  : 'merge' '(' histexpression (',' tagk)* ')'
  ;

histalign
  : 'align' '(' Number timeSuffix? ',' histexpression ')'
  ;

histdebug
  : 'debug' '(' histexpression (',' Number)? ')'
  ;

histExplain
  : 'explain' '(' histexpression ')'
  ;

histimpersonate
  : 'impersonate' '(' customer=customerId ',' histexpression ')'
  ;

// global filter for histexpression. tsexpressions in this context are tag or entity predicates.
histglobalFilter
  : 'globalFilter' '(' histexpression ',' tsexpression (',' tsexpression)* ')'
  ;

bucket
  : 'histo' '(' duration ',' expression (',' tagk)* ')'
  ;

cumulativeBucket
  : 'cumulativeHisto' '(' (duration ',')? (bucketName=tagk ',')? expression (',' tagKList)? ')'
  ;

cumulativePercentile
  : 'cumulativePercentile' '(' number ',' (bucketName=tagk ',')? expression (',' tagKList)? ')'
  ;

frequencyBucket
  : 'frequencyHisto' '(' (duration ',')? (bucketName=tagk ',')? expression (',' tagKList)? ')'
  ;

histalias
  : op='aliasMetric' '(' histexpression ',' (aliassource=tagk ',')? (sourceTagK=tagk ',')? regex=Quoted ',' replacement=Quoted ')'
  | op='aliasMetric' '(' histexpression ',' (aliassource=tagk ',')? (sourceTagK=tagk ',')? node=Number (',' separators=Quoted)? ')'
  | op='aliasMetric' '(' histexpression ',' replacement=Quoted ')'
  | op=('aliasHost' | 'aliasSource') '(' histexpression ',' (aliassource=tagk ',')? (sourceTagK=tagk ',')? regex=Quoted ',' replacement=Quoted ')'
  | op=('aliasHost' | 'aliasSource') '(' histexpression ',' (aliassource=tagk ',')? (sourceTagK=tagk ',')? node=Number (',' separators=Quoted)? ')'
  | op=('aliasHost' | 'aliasSource') '(' histexpression ',' replacement=Quoted ')'
  | op='taggify' '(' histexpression ',' aliassource=tagk ',' (sourceTagK=tagk ',')? newTagK=tagk ',' regex=Quoted ',' replacement=Quoted ')'
  | op='taggify' '(' histexpression ',' aliassource=tagk ',' (sourceTagK=tagk ',')? newTagK=tagk ',' node=Number (',' separators=Quoted)? ')'
  | op='taggify' '(' histexpression ',' newTagK=tagk ',' replacement=Quoted ')'
  ;

histLimit
  : op='limit' '(' (countNum=Number | countPercentNum=Number '%') (',' (offsetNum=Number | offsetPercentNum=Number '%'))? ',' histexpression ')'
  ;

// -> expression
size
  : 'count' '(' histexpression ')'
  ;

median
  : 'median' '(' histexpression ')'
  ;

quantile
  : 'percentile' '(' number ',' histexpression')'
  ;

stddev
  : 'stddev' '(' histexpression ')'
  ;

cdf
  : 'cdf' '(' number ',' histexpression')'
  ;

average
  : 'avg' '(' histexpression ')'
  ;

histmin
  : 'min' '(' histexpression ')'
  ;

histmax
  : 'max' '(' histexpression ')'
  ;

// SPANS

spanExpression
  : '(' spanExpression ')'
  | spanExpression set=('as'|'AS') variableName
  | getter
  | spanSeries
  | spanHighpass
  | spanLowpass
  | spanRootsOnly
  | spanChildrenOnly
  | spanUniqueTraces
  | spanCollect
  | spanDebug
  | spanImpersonate
  | spanLimit
  | left=spanExpression '.' op=Identifier '(' right=spanExpression ')'
  ;

spanSeries
  : 'spans' '(' tsexpression (',' tsexpression)* ')'
  ;

spanHighpass
  : 'highpass' '(' duration ',' spanExpression ')'
  ;

spanLowpass
  : 'lowpass' '(' duration ',' spanExpression ')'
  ;

spanRootsOnly
  : 'rootsOnly' '(' spanExpression ')'
  ;

spanChildrenOnly
  : 'childrenOnly' '(' spanExpression ')'
  ;

spanUniqueTraces
  : 'uniqueTraces' '(' spanExpression ')'
  ;

spanCollect
  : 'collect' '(' spanExpression (',' spanExpression)* ')'
  ;

spanDebug
  : 'debug' '(' spanExpression (',' Number)? ')'
  ;

spanExplain
  : 'explain' '(' spanExpression ')'
  ;

spanImpersonate
  : 'impersonate' '(' customer=customerId ',' spanExpression ')'
  ;

spanLimit
  : 'limit' '(' Number ',' spanExpression  ')'
  ;

// TRACE
traceExpression
  : '(' traceExpression ')'
  | traceExpression set=('as'|'AS') variableName
  | getter
  | traces
  | traceLimit
  | traceDebug
  | traceImpersonate
  | traceHighpass
  | traceLowpass
  ;

traces
  : 'traces' '(' spanExpression (',' spanExpression)* ')'
  | 'traces' '(' tsexpression (',' tsexpression)* ')'
  ;

traceLimit
  : 'limit' '(' Number ',' traceExpression ')'
  ;

traceDebug
  : 'debug' '(' traceExpression (',' Number)? ')'
  ;

traceExplain
  : 'explain' '(' traceExpression ')'
  ;

traceImpersonate
  : 'impersonate' '(' customer=customerId ',' traceExpression ')'
  ;

traceHighpass
  : 'highpass' '(' duration ',' traceExpression ')'
  ;

traceLowpass
  : 'lowpass' '(' duration ',' traceExpression ')'
  ;

// EDGES
edgesExpression
  : '(' edgesExpression ')'
  | edgesExpression set=('as'|'AS') variableName
  | getter
  | edges
  | edgesLimit
  | edgesDebug
  | edgesImpersonate
  ;

edges
  : 'edges' '(' tsexpression (',' tsexpression)* ')'
  ;

edgesLimit
  : 'limit' '(' Number ',' edgesExpression ')'
  ;

edgesDebug
  : 'debug' '(' edgesExpression (',' Number)? ')'
  ;

edgesExplain
  : 'explain' '(' edgesExpression ')'
  ;

edgesImpersonate
  : 'impersonate' '(' customer=customerId ',' edgesExpression ')'
  ;

apdexWithoutHistogram
  : 'apdex' '(' tag (',' tag)* ')'
  | 'apdex' '(' satisfiedLatency=number ',' tag (',' tag)* ')'
  | 'apdex' '(' satisfiedLatency=number ',' toleratingLatency=number ',' tag (',' tag)* ')'
  ;

apdexWithHistogram
  : 'apdex' '(' histexpression ')'
  | 'apdex' '(' tag (',' tag)* ',' histexpression ')'
  | 'apdex' '(' satisfiedLatency=number ',' histexpression ')'
  | 'apdex' '(' satisfiedLatency=number ',' toleratingLatency=number ',' histexpression ')'
  ;

apdexLatency
  : 'apdexLatency' '(' tag (',' tag)* (','  apdexLatencyThreshold=Identifier)? ')'
  ;

// EVENTS
eventsexpression
  : '(' eventsexpression ')'
  | eventsexpression op='union' eventsexpression
  | eventsexpression op='intersect' eventsexpression
  | eventsexpression op='-' eventsexpression
  | eventsexpression op='<' eventsexpression
  | eventsexpression op='>' eventsexpression
  | eventsexpression op='=' eventsexpression
  | eventsexpression op='m' eventsexpression
  | eventsexpression op='mi' eventsexpression
  | eventsexpression op='o' eventsexpression
  | eventsexpression op='oi' eventsexpression
  | eventsexpression op='s' eventsexpression
  | eventsexpression op='si' eventsexpression
  | eventsexpression op='d' eventsexpression
  | eventsexpression op='di' eventsexpression
  | eventsexpression op='f' eventsexpression
  | eventsexpression op='fi' eventsexpression
  | eventsexpression set=('as'|'AS') variableName
  | getter
  | events
  | asevents
  | firing
  | eventsGlobalFilter
  | eventsfunction1 '(' eventsexpression ')'
  | lagEvents
  | leadEvents
  | shiftEvents
  | eventliteral
  ;

eventliteral
  : 'since' '(' duration ')'
  | 'timespan' '(' Number ',' Number (',' tag)* ')'
  | 'timespan' '(' string ',' string (',' tag)* ')'
  ;

events
  : 'events' '(' eventTags ')'
  | 'events' '()'
  ;

eventsfunction1
  : 'until'
  | 'since'
  | 'coalesce'
  | 'first'
  | 'after'
  | 'last'
  | 'firstEnding'
  | 'lastEnding'
  | 'closed'
  ;

firing
  : 'firing' '(' duration ',' Number ',' Number ',' expression (',' tag)* ')'
  ;

eventsGlobalFilter
  : 'globalFilter' '(' eventsexpression ',' tsexpression (',' tsexpression)* ')'
  ;

asevents
  : aseventstransform '(' duration ',' duration ',' expression  (',' tag)* ')'
  ;

aseventstransform
  : 'nonzero'
  ;

lagEvents
  : op=('lagStart'|'lagEnd') '(' Number timeSuffix? ',' eventsexpression ')'
  ;

leadEvents
  : op=('leadStart'|'leadEnd') '(' Number timeSuffix? ',' eventsexpression ')'
  ;

shiftEvents
  : op=('shiftForward'|'shiftBackward') '(' Number timeSuffix? ',' eventsexpression ')'
  ;

duration
  : Number timeSuffix?
  ;

eventcount
  : 'count' '('eventsexpression ')'
  ;

eventsongoing
  : 'ongoing' '('eventsexpression ')'
  ;

joinBy
  : 'by' '(' tagk (',' tagk)* ')'
  | 'by' tagk
  ;

groupmodifier
  : 'groupLeft'
  | 'groupRight'
  ;

expression
  : '(' expression ')'
  // '*'|'/' and '-'|'+' should not be combined - this ensures correct order of operations
  | expression op='^' (joinBy)? (groupmodifier)? expression
  | expression raw='raw' '(' op='^' ')' (joinBy)? expression
  | expression bracket='[' op='^' ']' (joinBy)? (groupmodifier)? expression
  | expression raw='raw' '(' bracket='[' op='^' ']' ')' (joinBy)? expression
  | expression op=('*'|'/'|'%') (joinBy)? (groupmodifier)? expression
  | expression raw='raw' '(' op=('*'|'/'|'%') ')' (joinBy)? expression
  | expression bracket='[' op=('*'|'/'|'%') ']' (joinBy)? (groupmodifier)? expression
  | expression raw='raw' '(' bracket='[' op=('*'|'/'|'%') ']' ')' (joinBy)? expression
  | expression op=('-'|'+') (joinBy)? (groupmodifier)? expression
  | expression raw='raw' '(' op=('-'|'+') ')' (joinBy)? expression
  | expression bracket='[' op=('-'|'+') ']' (joinBy)? (groupmodifier)? expression
  | expression raw='raw' '(' bracket='[' op=('-'|'+') ']' ')' (joinBy)? expression
  | expression comparisonOperator (joinBy)? (groupmodifier)? expression
  | expression raw='raw' '(' comparisonOperator ')' (joinBy)? expression
  | expression bracket='[' comparisonOperator ']' (joinBy)? (groupmodifier)? expression
  | expression raw='raw' '(' bracket='[' comparisonOperator ']' ')' (joinBy)? expression
  | expression op=('or'|'and'|'OR'|'AND') (joinBy)? (groupmodifier)? expression
  | expression raw='raw' '(' op=('or'|'and'|'OR'|'AND') ')' (joinBy)? expression
  | expression bracket='[' op=('or'|'and'|'OR'|'AND') ']' (joinBy)? (groupmodifier)? expression
  | expression raw='raw' '(' bracket='[' op=('or'|'and'|'OR'|'AND') ']' ')' (joinBy)? expression
  | expression set=('as'|'AS') variableName
  | left=expression '.' exprRelation=expressionRelation '(' right=expression ')'
  | timeseries
  | counterseries
  | spansOngoing
  | data
  | iDeltaInternal
  | iRateInternal
  | rate
  | deriv
  | nonNegativeDerivative
  | iff
  | defaulted
  | math
  | stringManipulation
  | number
  | lag
  | lead
  | hide
  | reveal
  | last
  | next
  | interpolate
  | at
  | atEpoch
  | sum
  | avg
  | stddev
  | count
  | eventcount
  | eventsongoing
  | variance
  | min
  | missing
  | max
  | percentile
  | movingseriescount // this is more like an aggregate than a moving statistic
  | movinguniquevaluescount
  | rawsum
  | rawavg
  | rawcount
  | rawvariance
  | rawmin
  | rawmax
  | rawpercentile
  | countersum
  | getter
  | time
  | timestamp
  | hour
  | minute
  | weekday
  | day
  | dayOfYear
  | daysInMonth
  | isToday
  | month
  | year
  | between
  | any
  | all
  | flapping
  | top
  | retainSeries
  | removeSeries
  | globalFilter
  | collect
  | alias
  | debug
  | explain
  | impersonate
  | limit
  | exists
  | windowWidth
  | filter
  | quantile
  | cumulativePercentile
  | cdf
  | median
  | size
  | average
  | histmin
  | histmax
  | priority
  | summary
  | alignedSummary
  | joinFunction
  | intersect
  | complement
  | union
  | apdexWithHistogram
  | apdexWithoutHistogram
  | apdexLatency
  | modifyDimension
  ;

expressionRelation
  : Identifier
  | 'highpass' // These functions with same name defined already. So we have to explicitly define the name to make it work.
  | 'lowpass'
  | 'equals'
  | 'notEquals'
  ;

comparisonOperator
  : '=' | '>' | '<' | '<' '=' | '>' '=' | '!' '='
  ;

strictComparisonOperator
  : '=' | '!' '='
  ;

getter
  : '$' variableName
  ;

debug
  : 'debug' '(' expression (',' Number)? ')'
  ;

explain
  : 'explain' '(' expression ')'
  ;

impersonate
  : 'impersonate' '(' customer=customerId ',' expression ')'
  ;

priority
  : 'bestEffort' '(' expression ')'
  ;

limit
  : op='limit' '(' (countNum=Number | countPercentNum=Number '%') (',' (offsetNum=Number | offsetPercentNum=Number '%'))? ',' expression ')'
  | op='sample' '(' (countNum=Number | countPercentNum=Number '%') ',' expression ')'
  | op='random' '(' (countNum=Number | countPercentNum=Number '%') ',' expression ')'
  ;

exists
  : 'exists' '(' expression ')'
  ;

windowWidth
  : 'windowWidth' '()'
  ;

lag
  : 'lag' '(' (Number timeSuffix? | string) ',' expression ')'
  ;

lead
  : 'lead' '(' (Number timeSuffix? | string) ',' expression ')'
  ;

hide
  : 'hideAfter' '(' (Number timeSuffix? | string) ',' expression ')'
  ;

reveal
  : 'hideBefore' '(' (Number timeSuffix? | string) ',' expression ')'
  ;

last
  : 'last' '(' (Number timeSuffix? ',')? expression ')'
  ;

next
  : 'next' '(' (Number timeSuffix? ',')? expression ')'
  ;

interpolate
  : 'interpolate' '(' expression ')'
  ;

at
  : 'at' '(' (atNumber=Number atSuffix=timeSuffix? | atStr=string) ',' ((lookbackNumber=Number lookbackSuffix=timeSuffix? | lookbackStr=string) ',')? expression ')'
  ;

atEpoch
  : 'atEpoch' '(' number ',' expression ')'
  ;

iff
  : op=('if'|'rawif') '(' expression ',' expression (',' expression)? ')'
  ;

between
  : 'between' '(' expression ',' expression ',' expression ')'
  ;

any
  : 'any' '(' Number timeSuffix? ',' expression ')'
  ;

all
  : 'all' '(' Number timeSuffix? ',' expression ')'
  ;

flapping
  : 'flapping' '(' (Number timeSuffix? ',') expression ')'
  ;

top
  : op=('top'|'bottom') '(' Number ',' (summarization ',')? (Number timeSuffix? ',')? expression ')'
  | op=('topk'|'bottomk') '(' Number ',' (summarization ',')? (Number timeSuffix? ',')? expression ')'
  ;

percentile
  : 'percentile' '(' number ',' expression (',' tagk)* ')'
  | 'percentile' '(' number ',' expression relation=Identifier '(' tagk (',' tagk)* ')' ')'
  | 'percentile' '(' number ',' expression 'by' '(' tagk (',' tagk)* ')' ')'
  ;

rawpercentile
  : 'rawpercentile' '(' number ',' expression (',' tagk)* ')'
  | 'rawpercentile' '(' number ',' expression relation=Identifier '(' tagk (',' tagk)* ')' ')'
  | 'rawpercentile' '(' number ',' expression 'by' '(' tagk (',' tagk)* ')' ')'
  ;

missing
  : 'missing' '(' duration ',' expression (',' tagk)* ')'
  ;

movingseriescount
  : 'mseriescount' '(' (Number timeSuffix? ',') expression (',' tagk)*  ')'
  ;

movinguniquevaluescount
  : 'mvalues' '(' (Number timeSuffix? ',') expression (',' tagk)*  ')'
  ;

time
  : 'time' '()'
  ;


timestamp
  : 'timestamp' '(' expression ')'
  ;

hour
  : 'hour' '(' string ')'
  | 'hour' '(' string ',' expression ')'
  ;

minute
  : 'minute' '(' string ')'
  | 'minute' '(' string ',' expression ')'
  ;

day
  : 'day' '(' string ')'
  | 'day' '(' string ',' expression ')'
  ;

isToday
  : 'isToday' '(' string ')'
  | 'isToday' '(' string ',' expression ')'
  ;

weekday
  : 'weekday' '(' string ')'
  | 'weekday' '(' string ',' expression ')'
  ;

dayOfYear
  : 'dayOfYear' '(' string ')'
  | 'dayOfYear' '(' string ',' expression ')'
  ;

daysInMonth
  : 'daysInMonth' '(' string ')'
  | 'daysInMonth' '(' string ',' expression ')'
  ;

month
  : 'month' '(' string ')'
  | 'month' '(' string ',' expression ')'
  ;

year
  : 'year' '(' string ')'
  | 'year' '(' string ',' expression ')'
  ;

stringManipulation
  : stringFunction0 '(' aliassource=tagk ',' expression ')'
  | stringFunction1String '(' aliassource=tagk ',' paramQuoted=Quoted ',' expression ')'
  | stringFunction1Integer '(' aliassource=tagk ',' paramNumber=Number ',' expression ')'
  | stringFunction2IntegerInteger '(' aliassource=tagk ',' param1=Number ',' param2=Number ',' expression ')'
  ;

math
  : function0 '()'
  | function1 '(' expression ')'
  | function2 '(' expression ',' expression (',' join='inner' (joinBy)?)? ')'
  | haversine
  | movingcorrelation
  | movingaverage
  | mslope
  | holtwinters
  | arima
  | forecast
  | linearforecast
  | nnforecast
  | anomalous
  | legacy_anomalous
  | integrate
  | integral
  | movingstatistic
  | normalize
  | round
  ;

movingstatistic
  : movingvariance
  | movingcount
  | movingmin
  | movingmax
  | movingmedian
  | movingpercentile
  | movingsum
  | movingchanges
  | movinggeometricmean
  | movingavg2
  | movingdiff
  | downsample
  | align
  ;

alias
  : op='aliasMetric' '(' expression ',' (aliassource=tagk ',')? (sourceTagK=tagk ',')? regex=Quoted ',' replacement=Quoted ')'
  | op='aliasMetric' '(' expression ',' (aliassource=tagk ',')? (sourceTagK=tagk ',')? node=Number (',' separators=Quoted)? ')'
  | op='aliasMetric' '(' expression ',' replacement=Quoted ')'
  | op=('aliasHost' | 'aliasSource') '(' expression ',' (aliassource=tagk ',')? (sourceTagK=tagk ',')? regex=Quoted ',' replacement=Quoted ')'
  | op=('aliasHost' | 'aliasSource') '(' expression ',' (aliassource=tagk ',')? (sourceTagK=tagk ',')? node=Number (',' separators=Quoted)? ')'
  | op=('aliasHost' | 'aliasSource') '(' expression ',' replacement=Quoted ')'
  | op='taggify' '(' expression ',' aliassource=tagk ',' (sourceTagK=tagk ',')? newTagK=tagk ',' regex=Quoted ',' replacement=Quoted ')'
  | op='taggify' '(' expression ',' aliassource=tagk ',' (sourceTagK=tagk ',')? newTagK=tagk ',' node=Number (',' separators=Quoted)? ')'
  | op='taggify' '(' expression ',' newTagK=tagk ',' replacement=Quoted ')'
  ;

haversine
  : 'haversine' '(' expression ',' expression ',' expression ',' expression (',' join='inner' (joinBy)?)? ')'
  ;

movingcorrelation
  : 'mcorr' '(' (Number timeSuffix? ',') expression ',' expression (',' join='inner' (joinBy)?)? ')'
  ;

mslope
  : 'mslope' '(' duration ',' expression ')'
  ;

movingaverage
  : movingaverages '(' (Number timeSuffix? ',') expression ')'
  ;

movingaverages
  : 'mavg' | 'wmavg' | 'emavg'
  ;

// hw(<historyLen>, <seasonLen>, <sampleRate>, <tsExpression> [alpha,beta, gama])
// hw(<historyLen>, <tsExpression>, smoothingFactor(alpha), trendFactor(beta))
holtwinters
  : hwalias '(' (Number timeSuffix? ',') (Number timeSuffix? ',') (Number
  timeSuffix? ',') (expression)
  (',' Number)? (',' Number)? (',' Number)? (',' options=hwoptions)* ')'
  | hwalias '(' (duration ',') (expression) (',' Number) (',' Number) ')'
  ;

hwalias
  : 'hw' | 'holtwinters'
  ;

hwoptions
  : 'additive'
  | 'multiplicative'
  ;

arima
  // nothing, just go.
  : 'arima' '(' (expression)')'
  // just history and align.
  | 'arima' '(' (Number timeSuffix? ',') (Number timeSuffix? ',') (Number timeSuffix? ',')?
    (expression) ')' //
  // p, d, q, P, D, Q, m
  | 'arima' '(' (Number timeSuffix? ',') (Number timeSuffix? ',') (Number timeSuffix? ',')?
    (expression) ',' p=Number ',' d=Number ',' q=Number (',' options=arimaoptions)* ')' //
  // p, d, q, P, D, Q, m
  | 'arima' '(' (Number timeSuffix? ',') (Number timeSuffix? ',') (Number timeSuffix? ',')?
    (expression) ',' p=Number ',' d=Number ',' q=Number
    ',' capitalP=Number ',' capitalD=Number ',' capitalQ=Number (',' options=arimaoptions)* ')'
  ;

arimaoptions
  : 'with_constant'
  | 'with_drift'
  ;

forecast
  // just arima models (or CC default), history and align
  : 'forecast' '('
      (history=Number historySuffix=timeSuffix ',')?
      (alignment=Number alignmentSuffix=timeSuffix ',')?
      (confidence=Number ',')?
      (expression)
      (',' options=boundaryOptions)*')'
  // with additional options
  | 'forecast' '('
      (history=Number historySuffix=timeSuffix ',')?
      (alignment=Number alignmentSuffix=timeSuffix ',')?
      (confidence=Number ',')?
      (expression)
      (',' options=boundaryOptions)*
      (',' forecastTrendThreshold)?
      (',' forecastModel)* ')'
  ;

forecastModel
  : Quoted
  ;

forecastTrendThreshold
  : Number
  ;

linearforecast
  : 'linearforecast' '(' duration ',' duration ',' expression ')'
  ;

nnforecast
  : 'nnforecast' '('
  (forecastPeriod=Number forecastPeriodSuffix=timeSuffix ',')?
  (confidence=Number ',')?
  (expression)
  (',' options=boundaryOptions)* ')'
  ;

anomalous
  // anomalous captain crunch version
  : 'anomalous' '('
     (testWindow=Number testWindowSuffix=timeSuffix ',')
     (confidence=Number ',')?
     (history=Number historySuffix=timeSuffix ',')?
     (alignment=Number alignmentSuffix=timeSuffix ',')?
     (expression) ')'
  // anomalous neural network version
  | 'anomalous' '('
     (type=Identifier ',')?
     (testWindow=Number testWindowSuffix=timeSuffix ',')
     (confidence=Number ',')?
     (history=Number historySuffix=timeSuffix ',')?
     (expression)
     (',' options=boundaryOptions)* ')'
  ;

boundaryOptions
  : 'with_bounds'
  | 'only_bounds'
  ;

// MONIT-15856: legacy_anomalous with CC
legacy_anomalous
  : 'legacy_anomalous' '('
     (testWindow=Number testWindowSuffix=timeSuffix ',')
     (confidence=Number ',')?
     (history=Number historySuffix=timeSuffix ',')?
     (alignment=Number alignmentSuffix=timeSuffix ',')?
     (expression) ')';

integrate
  : 'integrate' '(' (Number timeSuffix? ',') expression ')'
  ;

integral
  : 'integral' '(' expression ')'
  ;

movingvariance
  : 'mvar' '(' (Number timeSuffix? ',') expression ')'
  ;

movingcount
  : 'mcount' '(' (Number timeSuffix? ',') expression ')'
  ;

movingmin
  : 'mmin' '(' (Number timeSuffix? ',') expression ')'
  ;

movingmax
  : 'mmax' '(' (Number timeSuffix? ',') expression ')'
  ;

movingavg2
  : 'mavg2' '(' (Number timeSuffix? ',') expression ')'
  ;

movingmedian
  : 'mmedian' '(' (Number timeSuffix? ',') expression ')'
  ;

movingpercentile
  : 'mpercentile' '(' (Number timeSuffix? ',') number ',' expression ')'
  ;

movingsum
  : 'msum' '(' (Number timeSuffix? ',') expression ')'
  ;

movingchanges
  : 'mchanges' '(' (Number timeSuffix? ',') expression ')'
  ;

movinggeometricmean
  : 'mgmean' '(' (Number timeSuffix? ',') expression ')'
  ;

movingdiff
  : 'mdiff' '(' (Number timeSuffix? ',') expression ')'
  ;

downsample
  : 'downsample' '(' (Number timeSuffix? ',') expression ')'
  ;

align
  : 'align' '(' (Number timeSuffix? ',') (summarization ',')? expression ')'
  ;

normalize
  : 'normalize' '(' expression ')'
  ;

round
  : 'round' '(' (Number ',')? expression ')'
  ;

function0
  : 'random'
  ;

function1
  : 'sin'       | 'cos'   | 'tan'
  | 'sinh'      | 'cosh'  | 'tanh'
  | 'asin'      | 'acos'  | 'atan'
  | 'abs'       | 'ceil'  | 'floor'
  | 'exp'       | 'log'   | 'log10'
  | 'sqrt'      | 'log2'  | 'toDegrees'
  | 'toRadians' | 'gamma'
  ;

function2
  : 'atan2'    | 'max'     | 'min' | 'pow'
  | 'highpass' | 'lowpass' | 'mod'
  | 'clampMax' | 'clampMin'
  ;

stringFunction0
  : 'length'   | 'isEmpty' | 'toLowerCase' | 'toUpperCase'
  | 'trim'     | 'strip'   | 'stripLeading' | 'stripTrailing'
  | 'isBlank'
  ;

stringFunction1String
  : 'equals'   | 'equalsIgnoreCase' | 'startsWith'
  | 'endsWith' | 'indexOf' | 'lastIndexOf'
  | 'concat'   | 'matches' | 'contains'
  |
  ;

stringFunction1Integer
  : 'charAt' | 'substring' | 'repeat'
  ;

stringFunction2IntegerInteger
  : 'substring'
  ;

string
  : Quoted
  ;

timeSuffix
  : 's' // seconds
  | 'm' // minutes
  | 'h' // hours
  | 'd' // days
  | 'w' // weeks
  | 'vw' // view widths
  | 'bw' // bucket widths
  | 'ms' // milliseconds
  ;

siSuffix
  : 'Y' // 10^24
  | 'Z' // 10^21
  | 'E' // 10^18
  | 'P' // 10^15
  | 'T' // 10^12
  | 'G' // 10^9
  | 'M' // 10^6
  | 'k' // 10^3
  | 'h' // 10^2
  | 'da' // 10^1
  | 'd' // 10^-1
  | 'c' // 10^-2
  | 'm' // 10^-3
  | 'µ' // 10^-6
  | 'n' // 10^-9
  | 'p' // 10^-12
  | 'f' // 10^-15
  | 'a' // 10^-18
  | 'z' // 10^-21
  | 'y' // 10^-24
  ;

number
  : MinusSign? Number (siSuffix)?
  | PlusSign? Number (siSuffix)?
  ;

defaulted
  : 'default' '(' (release=duration ',')? (attack=duration ',')? expression ',' expression ')'
  | 'defaultfor' '(' (release=duration ',')? expression ',' expression ')'
  | 'defaultafter' '(' (attack=duration ',')? expression ',' expression ')'
  ;

timeseries
  : type='ts' '(' tsexpression (',' tsexpression)* ')'
  ;

counterseries
  : type='cs' '(' tsexpression (',' tsexpression)* ')'
  ;

spansOngoing
  : 'ongoing' '(' spanExpression ')'
  ;

data
  : 'inlineData' '(' name=metric ',' host=metric (',' tag)* (',' dataTimeTuple)* ')'
  ;

dataTimeTuple
  : '[' timeUnit=Number ',' value=number ']'
  | value=number
  ;

retainSeries
  : 'retainSeries' '(' expression ',' tsexpression (',' tsexpression)* ')'
  ;

removeSeries
  : 'removeSeries' '(' expression ',' tsexpression (',' tsexpression)* ')'
  ;

globalFilter
  : 'globalFilter' '(' expression ',' tsexpression (',' tsexpression)* ')'
  ;

collect
  : 'collect' '(' expression (',' expression)+ ')'
  | 'collect' '(' expression ')' {notifyErrorListeners("collect expects two or more arguments");}
  ;

sum
  : 'sum' '(' expression (',' tagk)* ')'
  | 'sum' '(' expression relation=Identifier '(' tagk (',' tagk)* ')' ')'
  | 'sum' '(' expression 'by' '(' tagk (',' tagk)* ')' ')'
  ;

rawsum
  : 'rawsum' '(' expression (',' tagk)* ')'
  | 'rawsum' '(' expression relation=Identifier '(' tagk (',' tagk)* ')' ')'
  | 'rawsum' '(' expression 'by' '(' tagk (',' tagk)* ')' ')'
  ;

countersum
  : 'ratesum' '(' expression (',' tagk)* ')'
  | 'counter_sum' '(' expression (',' tagk)* ')'
  ;

avg
  : 'avg' '(' expression (',' tagk)* ')'
  | 'avg' '(' expression relation=Identifier '(' tagk (',' tagk)* ')' ')'
  | 'avg' '(' expression 'by' '(' tagk (',' tagk)* ')' ')'
  ;

rawavg
  : 'rawavg' '(' expression (',' tagk)* ')'
  | 'rawavg' '(' expression relation=Identifier '(' tagk (',' tagk)* ')' ')'
  | 'rawavg' '(' expression 'by' '(' tagk (',' tagk)* ')' ')'
  ;

count
  : 'count' '(' expression (',' tagk)* ')'
  | 'count' '(' expression relation=Identifier '(' tagk (',' tagk)* ')' ')'
  | 'count' '(' expression 'by' '(' tagk (',' tagk)* ')' ')'
  ;

rawcount
  : 'rawcount' '(' expression (',' tagk)* ')'
  | 'rawcount' '(' expression relation=Identifier '(' tagk (',' tagk)* ')' ')'
  | 'rawcount' '(' expression 'by' '(' tagk (',' tagk)* ')' ')'
  ;

variance
  : 'variance' '(' expression (',' tagk)* ')'
  | 'variance' '(' expression relation=Identifier '(' tagk (',' tagk)* ')' ')'
  | 'variance' '(' expression 'by' '(' tagk (',' tagk)* ')' ')'
  ;

rawvariance
  : 'rawvariance' '(' expression (',' tagk)* ')'
  | 'rawvariance' '(' expression relation=Identifier '(' tagk (',' tagk)* ')' ')'
  | 'rawvariance' '(' expression 'by' '(' tagk (',' tagk)* ')' ')'
  ;

min
  : 'min' '(' expression (',' tagk)* ')'
  | 'min' '(' expression relation=Identifier '(' tagk (',' tagk)* ')' ')'
  | 'min' '(' expression 'by' '(' tagk (',' tagk)* ')' ')'
  ;

rawmin
  : 'rawmin' '(' expression (',' tagk)* ')'
  | 'rawmin' '(' expression relation=Identifier '(' tagk (',' tagk)* ')' ')'
  | 'rawmin' '(' expression 'by' '(' tagk (',' tagk)* ')' ')'
  ;

max
  : 'max' '(' expression (',' tagk)* ')'
  | 'max' '(' expression relation=Identifier '(' tagk (',' tagk)* ')' ')'
  | 'max' '(' expression 'by' '(' tagk (',' tagk)* ')' ')'
  ;

rawmax
  : 'rawmax' '(' expression (',' tagk)* ')'
  | 'rawmax' '(' expression relation=Identifier '(' tagk (',' tagk)* ')' ')'
  | 'rawmax' '(' expression 'by' '(' tagk (',' tagk)* ')' ')'
  ;

iDeltaInternal
  : 'iDeltaInternal' '(' duration ',' expression ')'
  ;

iRateInternal
  : 'iRateInternal' '(' duration ',' expression ')'
  ;

rate
  : 'rate' '(' (duration ',')? expression ')'
  ;

deriv
  : 'deriv' '(' expression ')'
  ;

nonNegativeDerivative
  : 'ratediff' '(' (duration ',')? expression ')'
  | 'nonNegativeDerivative' '(' (duration ',')? expression ')'
  ;

filter
  : 'filter' '(' expression ',' tsexpression ')'
  ;

dimensionOp
  : 'retainDimension' | 'removeDimension'
  ;

modifyDimension
  : dimensionOp '(' expression ',' tagk (',' tagk)* ')'
  ;

tsexpression
  : tagPredicate
  | '(' tsexpression ')'
  | not=('not'|'NOT') tsexpression
  | tsexpression op=('or'|'and'|'OR'|'AND') tsexpression
  | metric
  ;

metric
  : Quoted
  | Literal
  | Letters
  | Identifier
  | RegularExpressionLiteral
  ;

tag
  : tagk eq=EQ tagv
  ;

tagPredicate
  : tagk tagComparisonOperator tagv
  ;

tagComparisonOperator
  : '=' | '!' '=' | '?' '='
  ;

eventTags
  : tagPredicate
  | ('not' 'not')+ eventTags
  | '(' eventTags ')'
  | (not = ('not'|'NOT')) eventTags
  | eventTags (and=(','|'and'|'AND') eventTags)+
  | eventTags (('or'|'OR') eventTags)+
  ;

tagk
  : Quoted
  | Letters
  | Literal
  | Identifier
  | Number
  | STAR '.'* (Literal | Letters | Identifier)?
  ;

customerId
  : Quoted
  | Letters
  | Identifier
  ;

tagv
  : Quoted
  | Letters
  | Literal
  | Identifier
  | Number
  | UID
  | IpV4Address
  | STAR '.'* (Literal | Letters | Identifier)?
  | RegularExpressionLiteral
  ;

variableName
  : Letters
  | Identifier
  ;

summarization
  : 'mean'
  | 'median'
  | 'max'
  | 'min'
  | 'sum'
  | 'count'
  | 'last'
  | 'first'
  ;

preprocessingFunction
  : 'rate'
  | 'deriv'
  ;

statsFunc
  : 'mean'
  | 'median'
  | 'avg'
  | 'max'
  | 'min'
  | Number
  ;

summary
  : 'summary' '(' (statsFunc ',')* histexpression ')'
  ;

alignedSummary
  : 'alignedSummary' '(' (statsFunc ',')* histexpression ')'
  ;

joinFunction
  : type=('join'|'rawjoin') '('
        source=joinSource ','
        outputs=joinOutput*
        valueExpression=joinValueExpression ')'
  ;

// expression must be a top-level variable assign (we'll check at the compiler)
joinSource
  : expression set=('as'|'AS') variableName joinPart*
  ;

// expression must be a top-level variable assign (we'll check at the compiler)
joinPart
  : inner=('inner'|'INNER')? ('join'|'JOIN') expression set=('as'|'AS') variableName
      (
        method=('on'|'ON') joinExpression
        | method=('using'|'USING') '(' using=tagKList ')'
      )
  | direction=('left' | 'LEFT' | 'right' | 'RIGHT' | 'full' | 'FULL') ('outer'|'OUTER')?
      ('join'|'JOIN') expression set=('as'|'AS') variableName
      (
        method=('on'|'ON') joinExpression
        | method=('using'|'USING') '(' using=tagKList ')'
      )
  ;

// ts1.host = ts2.host AND (ts1.region = ts2.region OR ts1.az = ts2.az)
joinExpression
  : left=joinExpression logicalOperator right=joinExpression
  | '(' joinExpression ')'
  | joinPredicate
  ;

logicalOperator
  : 'and' | 'AND' | 'or' | 'OR'
  ;

// ts1.host = ts2.host
joinPredicate
  : left=tagk strictComparisonOperator right=joinExpressionAtom
  ;

joinExpressionAtom
  : constant=Quoted
  | dimension=tagk
  ;

joinOutput
  : outputDimension=tagk '=' inputDimension=joinExpressionAtom ','
  ;

joinValueExpression
  : joinValueAtom
  | '(' joinValueExpression ')'
  | left=joinValueExpression op=('*'|'/') right=joinValueExpression
  | left=joinValueExpression op=('-'|'+') right=joinValueExpression
  | joinValueFunction
  | number
  ;

joinValueAtom
  : variableName
  | '{' variableName '|' number '}' // value or alternate if null.
  ;

joinValueFunction
  : func='max' '(' joinValueExpression (',' joinValueExpression)* ')'
  | func='min' '(' joinValueExpression (',' joinValueExpression)* ')'
  | func='avg' '(' joinValueExpression (',' joinValueExpression)* ')'
  | func='median' '(' joinValueExpression (',' joinValueExpression)* ')'
  | func='count' '(' joinValueExpression (',' joinValueExpression)* ')'
  | func='sum' '(' joinValueExpression (',' joinValueExpression)* ')'
  ;

intersect
  : 'intersect' '(' expression ',' expression (',' expression)* ')'
  ;

complement
  : 'complement' '(' expression ',' expression (',' expression)* ')'
  ;

union
  : 'union' '(' expression ',' expression (',' expression)* ')'
  ;

tagKList
  : tagk?
  | tagk (',' tagk)*
  ;