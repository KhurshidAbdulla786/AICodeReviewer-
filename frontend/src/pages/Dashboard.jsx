import React from 'react';
import Layout from '../components/Layout';
import { Link } from 'react-router-dom';
import { Code, Shield, Cpu, ArrowRight, CheckCircle, FileText, Activity } from 'lucide-react';

export default function Dashboard() {
  return (
    <Layout>
      <div className="space-y-8">
        {/* Hero Section */}
        <div className="bg-gradient-to-r from-primary-900/50 via-gray-900 to-gray-900 border border-primary-500/20 rounded-2xl p-8 shadow-xl">
          <div className="max-w-3xl">
            <span className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-medium bg-primary-500/10 text-primary-400 border border-primary-500/20 mb-4">
              <Cpu className="w-3.5 h-3.5" /> AI-Powered Code Reviewer
            </span>
            <h1 className="text-3xl sm:text-4xl font-extrabold text-white tracking-tight mb-4">
              Write cleaner, safer, and faster code with <span className="text-transparent bg-clip-text bg-gradient-to-r from-primary-400 to-accent-400">AI Intelligence</span>
            </h1>
            <p className="text-gray-300 text-lg mb-6">
              Instant static & AI code analysis, bug detection, automated refactoring suggestions, and deep code explanations powered by Spring AI & OpenAI/Gemini.
            </p>
            <div className="flex flex-wrap gap-4">
              <Link
                to="/editor"
                className="inline-flex items-center gap-2 bg-primary-600 hover:bg-primary-500 text-white font-medium px-6 py-3 rounded-xl transition-all shadow-lg shadow-primary-600/30"
              >
                <Code className="w-5 h-5" /> Start Code Review <ArrowRight className="w-4 h-4" />
              </Link>
              <Link
                to="/history"
                className="inline-flex items-center gap-2 bg-gray-800 hover:bg-gray-700 text-gray-200 font-medium px-6 py-3 rounded-xl transition-all border border-gray-700"
              >
                <FileText className="w-5 h-5" /> View History
              </Link>
            </div>
          </div>
        </div>

        {/* Features Grid */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="bg-gray-900/80 border border-gray-800 rounded-2xl p-6 hover:border-primary-500/40 transition-all">
            <div className="w-12 h-12 rounded-xl bg-primary-500/10 text-primary-400 flex items-center justify-center mb-4 border border-primary-500/20">
              <Code className="w-6 h-6" />
            </div>
            <h3 className="text-lg font-semibold text-white mb-2">Smart Code Analysis</h3>
            <p className="text-gray-400 text-sm">
              Comprehensive scoring on readability, naming conventions, SOLID principles, security, and performance.
            </p>
          </div>

          <div className="bg-gray-900/80 border border-gray-800 rounded-2xl p-6 hover:border-primary-500/40 transition-all">
            <div className="w-12 h-12 rounded-xl bg-accent-500/10 text-accent-400 flex items-center justify-center mb-4 border border-accent-500/20">
              <Shield className="w-6 h-6" />
            </div>
            <h3 className="text-lg font-semibold text-white mb-2">Bug & Vulnerability Detection</h3>
            <p className="text-gray-400 text-sm">
              Instantly detects null pointer risks, SQL injection, XSS, memory leaks, and infinite loops with severity ratings.
            </p>
          </div>

          <div className="bg-gray-900/80 border border-gray-800 rounded-2xl p-6 hover:border-primary-500/40 transition-all">
            <div className="w-12 h-12 rounded-xl bg-emerald-500/10 text-emerald-400 flex items-center justify-center mb-4 border border-emerald-500/20">
              <Activity className="w-6 h-6" />
            </div>
            <h3 className="text-lg font-semibold text-white mb-2">Interactive AI Assistant</h3>
            <p className="text-gray-400 text-sm">
              Chat with AI about your code context, get refactored code snippets, and detailed method explanations.
            </p>
          </div>
        </div>
      </div>
    </Layout>
  );
}
