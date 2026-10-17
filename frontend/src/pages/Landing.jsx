import React from 'react';
import { Link } from 'react-router-dom';
import { Bot, Code, Shield, Cpu, Zap, ArrowRight, Terminal } from 'lucide-react';

export default function Landing() {
  return (
    <div className="min-h-screen bg-gray-950 text-gray-100 selection:bg-primary-500 selection:text-white">
      {/* Navigation Header */}
      <header className="sticky top-0 z-50 backdrop-blur-xl bg-gray-950/80 border-b border-gray-800/80">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-20 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="w-11 h-11 rounded-2xl bg-gradient-to-tr from-primary-600 to-accent-600 flex items-center justify-center shadow-lg shadow-primary-500/25">
              <Bot className="w-6 h-6 text-white" />
            </div>
            <div>
              <span className="text-xl font-bold tracking-tight bg-clip-text text-transparent bg-gradient-to-r from-white via-gray-200 to-gray-400">
                AI Code Reviewer
              </span>
              <span className="block text-[10px] font-mono text-primary-400 tracking-wider uppercase">Enterprise Edition</span>
            </div>
          </div>

          <div className="flex items-center gap-4">
            <Link
              to="/login"
              className="text-sm font-medium text-gray-300 hover:text-white px-4 py-2 rounded-xl transition-colors"
            >
              Sign In
            </Link>
            <Link
              to="/register"
              className="bg-primary-600 hover:bg-primary-500 text-white text-sm font-medium px-5 py-2.5 rounded-xl transition-all shadow-lg shadow-primary-600/30 flex items-center gap-2"
            >
              Get Started <ArrowRight className="w-4 h-4" />
            </Link>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <section className="relative pt-24 pb-20 overflow-hidden">
        <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[600px] h-[600px] bg-primary-600/15 rounded-full blur-[140px] pointer-events-none"></div>

        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10 text-center">
          <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full text-xs font-medium bg-primary-500/10 text-primary-300 border border-primary-500/20 mb-8">
            <Zap className="w-3.5 h-3.5 text-primary-400" /> Powered by Spring AI & OpenAI/Gemini
          </div>

          <h1 className="text-4xl sm:text-6xl lg:text-7xl font-extrabold tracking-tight text-white max-w-5xl mx-auto leading-[1.1] mb-6">
            Autonomous Code Review & <span className="text-transparent bg-clip-text bg-gradient-to-r from-primary-400 via-accent-400 to-purple-400">AI Intelligence</span>
          </h1>

          <p className="text-lg sm:text-xl text-gray-400 max-w-3xl mx-auto mb-10 leading-relaxed">
            Detect critical bugs, enforce SOLID principles, prevent security vulnerabilities, and get instant refactoring recommendations with production-grade precision.
          </p>

          <div className="flex flex-col sm:flex-row items-center justify-center gap-4 mb-16">
            <Link
              to="/register"
              className="w-full sm:w-auto bg-primary-600 hover:bg-primary-500 text-white font-semibold px-8 py-4 rounded-xl transition-all shadow-xl shadow-primary-600/30 flex items-center justify-center gap-2 text-base"
            >
              <Cpu className="w-5 h-5" /> Start Free AI Review <ArrowRight className="w-5 h-5" />
            </Link>
            <Link
              to="/editor"
              className="w-full sm:w-auto bg-gray-900 hover:bg-gray-800 text-gray-200 font-semibold px-8 py-4 rounded-xl transition-all border border-gray-800 flex items-center justify-center gap-2 text-base"
            >
              <Terminal className="w-5 h-5 text-primary-400" /> Open Code Editor
            </Link>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-gray-950 border-t border-gray-800 py-12 text-center text-sm text-gray-500">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 flex flex-col sm:flex-row items-center justify-between gap-4">
          <p>© 2026 AI Code Reviewer. Enterprise Edition. All rights reserved.</p>
          <div className="flex items-center gap-6">
            <Link to="/login" className="hover:text-gray-300">Sign In</Link>
            <Link to="/register" className="hover:text-gray-300">Register</Link>
            <Link to="/dashboard" className="hover:text-gray-300">Dashboard</Link>
          </div>
        </div>
      </footer>
    </div>
  );
}
